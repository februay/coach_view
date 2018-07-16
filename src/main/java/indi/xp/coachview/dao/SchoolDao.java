package indi.xp.coachview.dao;

import java.util.List;

import indi.xp.coachview.model.School;

public interface SchoolDao {

    public School getSchoolById(String schoolId);

    public List<School> getSchoolByIdList(List<String> idList);

    public List<School> findSchoolList();

    public School addSchool(School school);

    public School updateSchool(School school);

}
