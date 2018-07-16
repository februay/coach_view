package indi.xp.coachview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.School;

@Mapper
public interface SchoolMapper {

    public School getSchoolById(@Param("schoolId") String schoolId);

    public List<School> getSchoolByIdList(@Param("idList") List<String> idList);

    public List<School> findSchoolList();

    public void addSchool(@Param("school") School school);

    public void updateSchool(@Param("school") School school);

}
