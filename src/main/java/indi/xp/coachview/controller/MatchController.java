package indi.xp.coachview.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import indi.xp.coachview.common.Constants;
import indi.xp.coachview.model.Match;
import indi.xp.coachview.model.MatchTeamInfo;
import indi.xp.coachview.model.MatchTeamMemberInfo;
import indi.xp.coachview.service.MatchService;
import indi.xp.coachview.service.MatchTeamInfoService;
import indi.xp.coachview.service.MatchTeamMemberInfoService;
import indi.xp.common.constants.MediaType;
import indi.xp.common.exception.BusinessException;
import indi.xp.common.restful.ResponseResult;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.ObjectUtils;
import indi.xp.common.utils.StringUtils;
import indi.xp.common.utils.excel.ExcelUtil;
import indi.xp.common.utils.excel.FileAnalysisUtils;

@RestController("matchController")
@RequestMapping("/match")
public class MatchController {

    private static final Logger logger = LoggerFactory.getLogger(MatchController.class);

    @Autowired
    private MatchService matchService;

    @Autowired
    private MatchTeamInfoService matchTeamInfoService;

    @Autowired
    private MatchTeamMemberInfoService matchTeamMemberInfoService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<Match> getById(@PathVariable(value = "id") String id,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(matchService.getById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<Match> add(@RequestBody Match match,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(matchService.add(match));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<Match> update(@RequestBody Match match,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(matchService.update(match));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
    public void delete(@PathVariable(value = "id") String id,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        matchService.delete(id);
    }

    /**
     * 比赛数据导出
     */
    @RequestMapping(value = "{matchId}/export", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public void export(@PathVariable("matchId") String matchId, HttpServletRequest request,
        HttpServletResponse response, @RequestHeader(value = Constants.Header.TOKEN, required = false) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {

        OutputStream out = null;
        HSSFWorkbook xlsFile = null;
        try {
            Match match = matchService.getById(matchId);
            if (match == null) {
                return;
            }
            List<MatchTeamInfo> matchTeamInfoList = matchTeamInfoService.findListByMatchId(matchId);
            List<MatchTeamMemberInfo> matchTeamMemberInfoList = matchTeamMemberInfoService.findListByMatchId(matchId);
            String fileName = match.getMatchName() + "-match-export.xls";
            xlsFile = this.createExcelFile(match, matchTeamInfoList, matchTeamMemberInfoList);
            ExcelUtil.setResponseHeader(response, fileName);
            out = response.getOutputStream();
            xlsFile.write(out);
            out.flush();
        } catch (Exception e) {
            logger.error("export match<{}>  error", matchId, e);
        } finally {
            ObjectUtils.safeClose(out);
        }
    }

    private HSSFWorkbook createExcelFile(Match match, List<MatchTeamInfo> matchTeamInfoList,
        List<MatchTeamMemberInfo> matchTeamMemberInfoList) {
        HSSFWorkbook workBook = null;
        List<List<String>> matchRowList = Match.parseToRowList(new ArrayList<>(Arrays.asList(match)));
        workBook = ExcelUtil.buildHSSFWorkbook(Match.defaultName, Match.defaultHeaderList, matchRowList, workBook);

        List<List<String>> matchTeamRowList = MatchTeamInfo.parseToRowList(matchTeamInfoList);
        workBook = ExcelUtil.buildHSSFWorkbook(MatchTeamInfo.defaultName, MatchTeamInfo.defaultHeaderList,
            matchTeamRowList, workBook);

        List<List<String>> matchTeamMemberRowList = MatchTeamMemberInfo.parseToRowList(matchTeamMemberInfoList);
        workBook = ExcelUtil.buildHSSFWorkbook(MatchTeamMemberInfo.defaultName, MatchTeamMemberInfo.defaultHeaderList,
            matchTeamMemberRowList, workBook);

        return workBook;
    }

    /**
     * 比赛数据导入
     */
    @RequestMapping(value = "import/{teamId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<Match> importMatchInfo(@RequestBody MultipartFile file, @PathVariable("teamId") String teamId,
        HttpServletRequest request, HttpServletResponse response,
        @RequestHeader(value = Constants.Header.TOKEN, required = false) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        Match match = null;
        if (file != null) {
            InputStream inputStream = null;
            try {
                inputStream = file.getInputStream();
                String fileName = file.getOriginalFilename();
                Map<String, List<List<String>>> resultMap = FileAnalysisUtils.convertToRowListMap(inputStream, fileName,
                    null);
                logger.info(JSON.toJSONString(resultMap));
                if (CollectionUtils.isNotEmpty(resultMap)) {
                    String matchId = null;
                    if (resultMap.containsKey(Match.defaultName)
                        && CollectionUtils.isNotEmpty(resultMap.get(Match.defaultName))) {
                        List<Match> matchList = Match.parseToObjectList(resultMap.get(Match.defaultName), teamId);
                        if (CollectionUtils.isNotEmpty(matchList)) {
                            match = matchService.add(matchList.get(0));
                            matchId = match.getMatchId();
                        }
                    }

                    if (StringUtils.isNotBlank(matchId)) {
                        if (resultMap.containsKey(MatchTeamInfo.defaultName)
                            && CollectionUtils.isNotEmpty(resultMap.get(MatchTeamInfo.defaultName))) {
                            List<MatchTeamInfo> matchTeamList = MatchTeamInfo
                                .parseToObjectList(resultMap.get(MatchTeamInfo.defaultName), teamId, matchId);
                            if (CollectionUtils.isNotEmpty(matchTeamList)) {
                                for (MatchTeamInfo matchTeam : matchTeamList) {
                                    matchTeamInfoService.add(matchTeam);
                                }
                            }
                        }
                        if (resultMap.containsKey(MatchTeamMemberInfo.defaultName)
                            && CollectionUtils.isNotEmpty(resultMap.get(MatchTeamMemberInfo.defaultName))) {
                            List<MatchTeamMemberInfo> matchTeamMemberList = MatchTeamMemberInfo
                                .parseToObjectList(resultMap.get(MatchTeamMemberInfo.defaultName), teamId, matchId);
                            if (CollectionUtils.isNotEmpty(matchTeamMemberList)) {
                                for (MatchTeamMemberInfo matchTeamMember : matchTeamMemberList) {
                                    matchTeamMemberInfoService.add(matchTeamMember);
                                }
                            }
                        }
                    }
                }
            } catch (BusinessException be) {
                logger.error("import match info file<" + file.getOriginalFilename() + "> error", be);
                throw be;
            } catch (Exception e) {
                logger.error("import match info file<" + file.getOriginalFilename() + "> error", e);
            } finally {
                ObjectUtils.safeClose(inputStream);
            }
        }
        return ResponseResult.buildResult(match);
    }

}
