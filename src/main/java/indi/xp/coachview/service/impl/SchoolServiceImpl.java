package indi.xp.coachview.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.SchoolDao;
import indi.xp.coachview.model.School;
import indi.xp.coachview.service.SchoolService;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolDao schoolDao;

    @Override
    public School getById(String id) {
        return schoolDao.getSchoolById(id);
    }

    @Override
    public List<School> findByIdList(List<String> idList) {
        return schoolDao.getSchoolByIdList(idList);
    }

    @Override
    public List<School> findList() {
        return schoolDao.findSchoolList();
    }

    @Override
    public School add(School school) {
        String currentTime = DateUtils.getDateTime();
        school.setSchoolId(UuidUtils.generateUUID());
        school.setDeleteStatus(false);
        school.setStatus("1");
        school.setCreateTime(currentTime);
        school.setActiveTime(currentTime);
        return schoolDao.addSchool(school);
    }

    @Override
    public School update(School school) {
        School dbSchool = school != null ? this.getById(school.getSchoolId()) : null;
        if (dbSchool != null) {
            return schoolDao.updateSchool(school);
        }
        return dbSchool;
    }

}
