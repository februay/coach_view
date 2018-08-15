package indi.xp.coachview.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.MatchDao;
import indi.xp.coachview.model.Match;
import indi.xp.coachview.model.MatchTeamInfo;
import indi.xp.coachview.model.vo.TeamSingleMatchDataVo;
import indi.xp.coachview.service.MatchService;
import indi.xp.coachview.service.MatchTeamInfoService;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchDao matchDao;

    @Autowired
    private MatchTeamInfoService matchTeamInfoService;

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
        return matchDao.add(match);
    }

    @Override
    public Match update(Match match) {
        Match dbMatch = match != null ? this.getById(match.getMatchId()) : null;
        if (dbMatch != null) {
            // TODO:
            return matchDao.update(dbMatch);
        }
        return dbMatch;
    }

    @Override
    public void delete(String id) {
        Match dbMatch = this.getById(id);
        if (dbMatch != null) {
            matchDao.delete(id);
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
        MatchTeamInfo opponentTeamInfo = matchTeamInfoService.getByMatchId(matchId, true);

        TeamSingleMatchDataVo vo = new TeamSingleMatchDataVo();
        vo.setMatchInfo(matchInfo);
        vo.setTeamInfo(teamInfo);
        vo.setOpponentTeamInfo(opponentTeamInfo);
        return vo;
    }

    @Override
    public List<Map<String, Object>> statTeamMemberMatchDataInfo(String teamId) {
        List<Map<String, Object>> memberAvgList = matchDao.statTeamMemberAvgMatchDataInfo(teamId);
        List<Map<String, Object>> memberDetailList = matchDao.statTeamMemberDetailMatchDataInfo(teamId);

        // 为每个球员数据添加明细数据
        if (CollectionUtils.isNotEmpty(memberAvgList)) {
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

}
