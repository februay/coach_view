package indi.xp.coachview.service;

import java.util.List;
import java.util.Map;

import indi.xp.coachview.model.Match;
import indi.xp.coachview.model.vo.SingleTeamMatchDataVo;
import indi.xp.coachview.model.vo.TeamSingleMatchDataVo;

public interface MatchService {

    public Match getById(String id);

    public List<Match> findByIdList(List<String> idList);

    public Match add(Match match);

    public Match update(Match match);

    public void delete(String id);

    public void batchDelete(List<String> idList);

    public List<Match> findListByTeamId(String teamId);

    /**
     * 每个球队场均数据等：场均控球率、场均跑动距离、场均传球成功率、总进球数、场均进球数、总失球数、场均失球数、 胜场数、平场数、负场数
     */
    public List<Map<String, Object>> statTeamMatchDataInfo(String clubId, String schoolId, String teamId);

    /**
     * 单个球队单场次数据：比赛信息， 球队数据、对手球队数据
     */
    public TeamSingleMatchDataVo statTeamSingleMatchDataInfo(String matchId);

    /**
     * 单个球队每个球员数据：比赛信息， 球队数据、对手球队数据
     */
    public List<Map<String, Object>> statTeamMemberMatchDataInfo(String teamId, boolean withDetails);

    /**
     * 单个球队对比对手数据
     */
    public SingleTeamMatchDataVo statSingleTeamMatchDataInfo(String teamId, boolean withDetails, Map<String, Object> params);

}
