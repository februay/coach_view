package indi.xp.coachview.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import indi.xp.coachview.model.Team;
import indi.xp.coachview.model.TeamCoach;
import indi.xp.coachview.model.TeamMember;
import indi.xp.coachview.model.TeamTeachVideo;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.model.vo.TeamVo;
import indi.xp.coachview.service.MatchService;
import indi.xp.coachview.service.TeamCoachService;
import indi.xp.coachview.service.TeamMemberService;
import indi.xp.coachview.service.TeamService;
import indi.xp.coachview.service.TeamTeachVideoService;
import indi.xp.common.constants.MediaType;
import indi.xp.common.exception.BusinessException;
import indi.xp.common.restful.ResponseResult;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.ObjectUtils;
import indi.xp.common.utils.excel.CsvBuilderUtils;
import indi.xp.common.utils.excel.FileAnalysisUtils;

@RestController("teamController")
@RequestMapping("/team")
public class TeamController {

    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamMemberService teamMemberService;

    @Autowired
    private TeamCoachService teamCoachService;

    @Autowired
    private TeamTeachVideoService teamTeachVideoService;

    @Autowired
    private MatchService matchService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<List<TeamVo>> findTeamList(
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(teamService.findList(true));
    }

    @RequestMapping(value = "list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<List<ListItemVo>> findTeamItemList(
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(teamService.findTeamItemList());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<TeamVo> getTeamById(@PathVariable(value = "id") String id,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(teamService.getById(id));
    }

    @RequestMapping(value = "{id}/team-member", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<List<TeamMember>> findTeamMemberListByTeamId(@PathVariable(value = "id") String id,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(teamMemberService.findTeamMemberListByTeamId(id));
    }

    @RequestMapping(value = "{id}/team-coach", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<List<TeamCoach>> findTeamCoachListByTeamId(@PathVariable(value = "id") String id,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(teamCoachService.findTeamCoachListByTeamId(id));
    }

    @RequestMapping(value = "{id}/team-teach-video", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<List<TeamTeachVideo>> findTeamTeachVideoListByTeamId(@PathVariable(value = "id") String id,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(teamTeachVideoService.findListByTeamId(id));
    }

    @RequestMapping(value = "{id}/team-match", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<List<Match>> findTeamMatchListByTeamId(@PathVariable(value = "id") String id,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(matchService.findListByTeamId(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<Team> addTeam(@RequestBody Team team,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(teamService.add(team));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<Team> updateTeam(@RequestBody Team team,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(teamService.update(team));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
    public void deleteTeam(@PathVariable(value = "id") String id,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {

        teamService.delete(id);
    }

    /**
     * 球队队员导出
     */
    @RequestMapping(value = "{teamId}/export-members", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public void exportTeamMemberList(@PathVariable("teamId") String teamId, HttpServletRequest request,
        HttpServletResponse response, @RequestHeader(value = Constants.Header.TOKEN, required = false) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File csvFile = null;
        try {
            Team team = teamService.getById(teamId);
            List<TeamMember> memberList = teamMemberService.findTeamMemberListByTeamId(teamId);
            String fileName = team.getTeamName() + "-members-export.csv";

            csvFile = this.createCSVFile(team, memberList, fileName);

            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8")); // 此处需要encodefileName来转换编码，否则中文会被过滤掉
            response.setHeader("Content-Length", String.valueOf(csvFile.length()));
            response.setContentType("application/csv; charset=utf-8");
            response.setCharacterEncoding("UTF-8");

            bis = new BufferedInputStream(new FileInputStream(csvFile));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            while (true) {
                int bytesRead;
                if (-1 == (bytesRead = bis.read(buff, 0, buff.length)))
                    break;
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            logger.error("export team<{}> members error", teamId, e);
        } finally {
            ObjectUtils.safeClose(bis, bos);
            if (csvFile != null) {
                csvFile.delete();
            }
        }

    }

    private File createCSVFile(Team team, List<TeamMember> memberList, String fileName) {
        List<String> headerList = TeamMember.defaultHeaderList;
        List<List<String>> rowList = TeamMember.parseToRowList(memberList);
        return CsvBuilderUtils.createCSVFile(headerList, rowList, null, fileName);
    }

    /**
     * 球队队员导入
     */
    @RequestMapping(value = "{teamId}/import-members", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<List<TeamMember>> importTeamMemberList(@RequestBody MultipartFile file,
        HttpServletRequest request, HttpServletResponse response, @PathVariable("teamId") String teamId,
        @RequestHeader(value = Constants.Header.TOKEN, required = false) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        Team team = teamService.getById(teamId);
        if (file != null && team != null) {
            InputStream inputStream = null;
            try {
                inputStream = file.getInputStream();
                String fileName = file.getOriginalFilename();
                Map<String, List<List<String>>> resultMap = FileAnalysisUtils.convertToRowListMap(inputStream, fileName,
                    null);
                logger.info(JSON.toJSONString(resultMap));

                for (Map.Entry<String, List<List<String>>> entry : resultMap.entrySet()) {
                    if (CollectionUtils.isNotEmpty(entry.getValue())) {
                        List<TeamMember> memberList = TeamMember.parseToObjectList(entry.getValue(), teamId,
                            team.getTeamName());
                        for (TeamMember member : memberList) {
                            teamMemberService.add(member);
                        }
                        break;
                    }
                }

            } catch (BusinessException be) {
                logger.error("import team<{}> members  file<" + file.getOriginalFilename() + "> error", teamId, be);
                throw be;
            } catch (Exception e) {
                logger.error("import team<{}> members  file<" + file.getOriginalFilename() + "> error", teamId, e);
            } finally {
                ObjectUtils.safeClose(inputStream);
            }
        }
        return ResponseResult.buildResult(teamMemberService.findTeamMemberListByTeamId(teamId));
    }

}
