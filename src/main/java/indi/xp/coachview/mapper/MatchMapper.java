package indi.xp.coachview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.Match;

@Mapper
public interface MatchMapper {

    public Match getById(@Param("id") String id, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<Match> findByIdList(@Param("idList") List<String> idList,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<Match> findList(@Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void add(@Param("entity") Match match);

    public void update(@Param("entity") Match match, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void delete(@Param("id") String id, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void batchDelete(@Param("idList") List<String> idList,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public Match getByWhere(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<Match> findByWhere(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public Integer queryCount(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void updateByWhere(@Param("updateMap") Map<String, Object> updateMap,
        @Param("paramMap") Map<String, Object[]> paramMap, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<Map<String, Object>> statTeamMatchDataInfo(@Param("clubId") String clubId,
        @Param("schoolId") String schoolId, @Param("teamId") String teamId,
        @Param("authFilterMap") Map<String, Object[]> buildAuthFilterMap);

    /**
     * 单个球队每个球员平均数据
     */
    public List<Map<String, Object>> statTeamMemberAvgMatchDataInfo(@Param("teamId") String teamId,
        @Param("authFilterMap") Map<String, Object[]> buildAuthFilterMap);

    /**
     * 单个球队每个球员明细数据
     */
    public List<Map<String, Object>> statTeamMemberDetailMatchDataInfo(@Param("teamId") String teamId,
        @Param("authFilterMap") Map<String, Object[]> buildAuthFilterMap);
}
