package indi.xp.coachview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.MatchTeamDetailInfo;

@Mapper
public interface MatchTeamDetailInfoMapper {

    public MatchTeamDetailInfo getById(@Param("id") String id,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<MatchTeamDetailInfo> findByIdList(@Param("idList") List<String> idList,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<MatchTeamDetailInfo> findList(@Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void add(@Param("entity") MatchTeamDetailInfo matchTeamDetailInfo);

    public void update(@Param("entity") MatchTeamDetailInfo matchTeamDetailInfo,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void delete(@Param("id") String id, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void batchDelete(@Param("idList") List<String> idList,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public MatchTeamDetailInfo getByWhere(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<MatchTeamDetailInfo> findByWhere(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public Integer queryCount(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void updateByWhere(@Param("updateMap") Map<String, Object> updateMap,
        @Param("paramMap") Map<String, Object[]> paramMap, @Param("authFilterMap") Map<String, Object[]> authFilterMap);
}
