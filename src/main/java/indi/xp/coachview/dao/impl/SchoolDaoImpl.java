package indi.xp.coachview.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import indi.xp.coachview.dao.SchoolDao;
import indi.xp.coachview.mapper.SchoolMapper;
import indi.xp.coachview.model.School;

@Repository
public class SchoolDaoImpl implements SchoolDao {

    @Autowired
    private SchoolMapper schoolMapper;

    @Override
    public School getSchoolById(String schoolId) {
        return schoolMapper.getSchoolById(schoolId);
    }

    @Override
    public List<School> getSchoolByIdList(List<String> idList) {
        return schoolMapper.getSchoolByIdList(idList);
    }

    @Override
    public List<School> findSchoolList() {
        return schoolMapper.findSchoolList();
    }

    @Override
    public School addSchool(School school) {
        schoolMapper.addSchool(school);
        return school;
    }

    @Override
    public School updateSchool(School school) {
        schoolMapper.updateSchool(school);
        return school;
    }

}
