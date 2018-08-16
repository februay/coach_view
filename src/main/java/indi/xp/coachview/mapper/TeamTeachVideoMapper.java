package indi.xp.coachview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.TeamTeachVideo;

@Mapper
public interface TeamTeachVideoMapper {

    public TeamTeachVideo getById(@Param("id") String id, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void add(@Param("entity") TeamTeachVideo teachVideo);

    public void update(@Param("entity") TeamTeachVideo teachVideo,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void delete(@Param("id") String id, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void batchDelete(@Param("idList") List<String> idList,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public Integer queryCount(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void updateByWhere(@Param("updateMap") Map<String, Object> updateMap,
        @Param("paramMap") Map<String, Object[]> paramMap, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public TeamTeachVideo getByWhere(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<TeamTeachVideo> findByWhere(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);
}
