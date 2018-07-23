package indi.xp.coachview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.School;

@Mapper
public interface SchoolMapper {

    public School getSchoolById(@Param("id") String id);

    public List<School> getSchoolByIdList(@Param("idList") List<String> idList);

    public List<School> findSchoolList();

    public void addSchool(@Param("school") School school);

    public void updateSchool(@Param("school") School school);

    public void delete(@Param("id") String id);

    public void batchDelete(@Param("idList") List<String> idList);

    public List<School> getByWhere(@Param("paramMap") Map<String, Object[]> paramMap);

    public List<School> findByWhere(@Param("paramMap") Map<String, Object[]> paramMap);

    public Integer queryCount(@Param("paramMap") Map<String, Object[]> paramMap);

    public void updateByWhere(@Param("updateMap") Map<String, Object> updateMap,
        @Param("paramMap") Map<String, Object[]> paramMap);

}
