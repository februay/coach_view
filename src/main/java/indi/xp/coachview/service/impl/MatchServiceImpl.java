package indi.xp.coachview.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.common.Constants;
import indi.xp.coachview.dao.MatchDao;
import indi.xp.coachview.model.Match;
import indi.xp.coachview.model.MatchTeamInfo;
import indi.xp.coachview.model.vo.MatchTeamInfoVo;
import indi.xp.coachview.model.vo.SingleTeamMatchDataVo;
import indi.xp.coachview.model.vo.TeamSingleMatchDataVo;
import indi.xp.coachview.service.MatchService;
import indi.xp.coachview.service.MatchTeamDetailInfoService;
import indi.xp.coachview.service.MatchTeamInfoService;
import indi.xp.coachview.service.MatchTeamMemberInfoService;
import indi.xp.coachview.session.SessionConext;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchDao matchDao;

    @Autowired
    private MatchTeamInfoService matchTeamInfoService;

    @Autowired
    private MatchTeamDetailInfoService matchTeamDetailInfoService;

    @Autowired
    private MatchTeamMemberInfoService matchTeamMemberInfoService;

    @Override
    public Match getById(String id) {
        return matchDao.getById(id);
    }

    @Override
    public List<Match> findByIdList(List<String> idList) {
        return matchDao.findByIdList(idList);
    }

    @Override
    public Match add(Match match) {
        String currentTime = DateUtils.getDateTime();
        match.setMatchId(UuidUtils.generateUUID());
        match.setCreateTime(currentTime);
        match.setDeleteStatus(false);

        SessionConext sessionContext = SessionConext.getThreadLocalSessionContext();
        if (sessionContext != null) {
            match.setCreatorId(sessionContext.getUid());
        }

        return matchDao.add(match);
    }

    @Override
    public Match update(Match match) {
        Match dbMatch = match != null ? this.getById(match.getMatchId()) : null;
        if (dbMatch != null) {
            if (match.getMatchName() != null) {
                dbMatch.setMatchName(match.getMatchName());
            }
            if (match.getMatchNumber() != null) {
                dbMatch.setMatchNumber(match.getMatchNumber());
            }
            if (match.getMatchSession() != null) {
                dbMatch.setMatchSession(match.getMatchSession());
            }
            if (match.getMatchTime() != null) {
                dbMatch.setMatchTime(match.getMatchTime());
            }
            if (match.getMatchVideo() != null) {
                dbMatch.setMatchVideo(match.getMatchVideo());
            }
            if (match.getVideoCoverImg() != null) {
                dbMatch.setVideoCoverImg(match.getVideoCoverImg());
            }
            if (match.getRealPlaceImg() != null) {
                dbMatch.setRealPlaceImg(match.getRealPlaceImg());
            }
            if (match.getAttackAreaImg() != null) {
                dbMatch.setAttackAreaImg(match.getAttackAreaImg());
            }
            return matchDao.update(dbMatch);
        }
        return dbMatch;
    }

    @Override
    public void delete(String id) {
        Match dbMatch = this.getById(id);
        if (dbMatch != null) {
            matchDao.delete(id);

            // 级联删除
            matchTeamInfoService.deleteByMatchId(id);
            matchTeamDetailInfoService.deleteByMatchId(id);
            matchTeamMemberInfoService.deleteByMatchId(id);
        }
    }

    @Override
    public List<Match> findListByTeamId(String teamId) {
        return matchDao.findListByTeamId(teamId);
    }

    @Override
    public void batchDelete(List<String> idList) {
        matchDao.batchDelete(idList);
    }

    @Override
    public List<Map<String, Object>> statTeamMatchDataInfo(String clubId, String schoolId, String teamId) {
        return matchDao.statTeamMatchDataInfo(clubId, schoolId, teamId);
    }

    @Override
    public TeamSingleMatchDataVo statTeamSingleMatchDataInfo(String matchId) {
        Match matchInfo = this.getById(matchId);
        MatchTeamInfo teamInfo = matchTeamInfoService.getByMatchId(matchId, false);
        MatchTeamInfoVo teamInfoVo = new MatchTeamInfoVo(teamInfo);
        teamInfoVo.setDetails(matchTeamDetailInfoService.findListByMatchId(matchId, false));

        MatchTeamInfo opponentTeamInfo = matchTeamInfoService.getByMatchId(matchId, true);
        MatchTeamInfoVo opponentTeamInfoVo = new MatchTeamInfoVo(opponentTeamInfo);
        opponentTeamInfoVo.setDetails(matchTeamDetailInfoService.findListByMatchId(matchId, true));

        TeamSingleMatchDataVo vo = new TeamSingleMatchDataVo();
        vo.setMatchInfo(matchInfo);
        vo.setTeamInfo(teamInfoVo);
        vo.setOpponentTeamInfo(opponentTeamInfoVo);
        return vo;
    }

    @Override
    public List<Map<String, Object>> statTeamMemberMatchDataInfo(String teamId, boolean widgetDetails) {
        List<Map<String, Object>> memberAvgList = matchDao.statTeamMemberAvgMatchDataInfo(teamId);

        // 为每个球员数据添加明细数据
        if (CollectionUtils.isNotEmpty(memberAvgList) && widgetDetails) {
            List<Map<String, Object>> memberDetailList = matchDao.statTeamMemberDetailMatchDataInfo(teamId);
            for (Map<String, Object> memberAvg : memberAvgList) {
                String memberId = String.valueOf(memberAvg.get("memberId"));
                List<Map<String, Object>> details = new ArrayList<>();
                for (Map<String, Object> memberDetail : memberDetailList) {
                    if (memberId.equals(String.valueOf(memberDetail.get("memberId")))) {
                        details.add(memberDetail);
                    }
                }
                memberAvg.put("details", details);
            }
        }
        return memberAvgList;
    }

    @Override
    public SingleTeamMatchDataVo statSingleTeamMatchDataInfo(String teamId, boolean withDetails,
        Map<String, Object> params) {
        SingleTeamMatchDataVo result = new SingleTeamMatchDataVo();
        List<Map<String, Object>> teamAvgList = matchDao.statSingleTeamAvgMatchDataInfo(teamId, params);
        if (CollectionUtils.isNotEmpty(teamAvgList)) {
            for (Map<String, Object> teamAvg : teamAvgList) {
                if (Constants.TRUE.equals(String.valueOf(teamAvg.get("opponent")))) {
                    result.setOpponentTeamInfo(teamAvg);
                } else {
                    result.setTeamInfo(teamAvg);
                }
            }
            if (withDetails) {
                List<Map<String, Object>> teamDetailList = matchDao.statSingleTeamDetailMatchDataInfo(teamId, params);
                List<Map<String, Object>> teamDetails = new ArrayList<>();
                List<Map<String, Object>> opponentTeamDetails = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(teamDetailList)) {
                    for (Map<String, Object> teamDetail : teamDetailList) {
                        if (Constants.TRUE.equals(String.valueOf(teamDetail.get("opponent")))) {
                            opponentTeamDetails.add(teamDetail);
                        } else if (Constants.FALSE.equals(String.valueOf(teamDetail.get("opponent")))) {
                            teamDetails.add(teamDetail);
                        }
                    }
                }
                if (result.getTeamInfo() != null) {
                    int consecutiveWins = 0; // 连胜
                    for (Map<String, Object> info : teamDetails) {
                        String matchResult = String.valueOf(info.get("matchResult"));
                        if (MatchTeamInfo.MATCH_RESULT_WIN.equals(matchResult)) {
                            consecutiveWins++;
                        } else {
                            consecutiveWins = 0;
                        }
                    }
                    result.getTeamInfo().put("details", teamDetails);
                    result.getTeamInfo().put("consecutiveWins", consecutiveWins);
                }
                if (result.getOpponentTeamInfo() != null) {
                    int consecutiveWins = 0; // 连胜
                    for (Map<String, Object> info : opponentTeamDetails) {
                        String matchResult = String.valueOf(info.get("matchResult"));
                        if (MatchTeamInfo.MATCH_RESULT_WIN.equals(matchResult)) {
                            consecutiveWins++;
                        } else {
                            consecutiveWins = 0;
                        }
                    }
                    result.getOpponentTeamInfo().put("details", opponentTeamDetails);
                    result.getOpponentTeamInfo().put("consecutiveWins", consecutiveWins);
                }
            }
        }
        return result;
    }

}
