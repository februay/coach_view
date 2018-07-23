package indi.xp.coachview.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import indi.xp.coachview.dao.SchoolDao;
import indi.xp.coachview.mapper.SchoolMapper;
import indi.xp.coachview.model.School;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.StringUtils;

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

    @Override
    public void delete(String id) {
        if (StringUtils.isNotBlank(id)) {
            schoolMapper.delete(id);
        }
    }

    @Override
    public void batchDelete(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            schoolMapper.batchDelete(idList);
        }
    }

    @Override
    public List<School> findListByClubId(String clubId) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("club_id", new String[] { clubId });
        return this.findByWhere(paramMap);
    }

    private List<School> findByWhere(Map<String, Object[]> paramMap) {
        return schoolMapper.findByWhere(paramMap);
    }

}
