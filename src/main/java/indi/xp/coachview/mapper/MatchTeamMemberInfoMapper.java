package indi.xp.coachview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.MatchTeamMemberInfo;

@Mapper
public interface MatchTeamMemberInfoMapper {

    public MatchTeamMemberInfo getById(@Param("id") String id,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<MatchTeamMemberInfo> findByIdList(@Param("idList") List<String> idList,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<MatchTeamMemberInfo> findList(@Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void add(@Param("entity") MatchTeamMemberInfo matchTeamMemberInfo);

    public void update(@Param("entity") MatchTeamMemberInfo matchTeamMemberInfo,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void delete(@Param("id") String id, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void batchDelete(@Param("idList") List<String> idList,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public MatchTeamMemberInfo getByWhere(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<MatchTeamMemberInfo> findByWhere(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public Integer queryCount(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void updateByWhere(@Param("updateMap") Map<String, Object> updateMap,
        @Param("paramMap") Map<String, Object[]> paramMap, @Param("authFilterMap") Map<String, Object[]> authFilterMap);
}
