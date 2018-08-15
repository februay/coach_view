package indi.xp.coachview.dao;

import java.util.List;
import java.util.Map;

import indi.xp.coachview.model.Match;

public interface MatchDao {

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
     * 单个球队每个球员平均数据
     */
    public List<Map<String, Object>> statTeamMemberAvgMatchDataInfo(String teamId);

    /**
     * 单个球队每个球员明细数据
     */
    public List<Map<String, Object>> statTeamMemberDetailMatchDataInfo(String teamId);

}
