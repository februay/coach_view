package indi.xp.coachview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.MatchTeamInfo;

@Mapper
public interface MatchTeamInfoMapper {

    public MatchTeamInfo getById(@Param("id") String id, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<MatchTeamInfo> findByIdList(@Param("idList") List<String> idList,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<MatchTeamInfo> findList(@Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void add(@Param("entity") MatchTeamInfo matchTeamInfo);

    public void update(@Param("entity") MatchTeamInfo matchTeamInfo,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void delete(@Param("id") String id, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void batchDelete(@Param("idList") List<String> idList,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public MatchTeamInfo getByWhere(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<MatchTeamInfo> findByWhere(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public Integer queryCount(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void updateByWhere(@Param("updateMap") Map<String, Object> updateMap,
        @Param("paramMap") Map<String, Object[]> paramMap, @Param("authFilterMap") Map<String, Object[]> authFilterMap);
}
