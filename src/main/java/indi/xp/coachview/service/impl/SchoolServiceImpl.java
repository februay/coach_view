package indi.xp.coachview.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.SchoolDao;
import indi.xp.coachview.model.School;
import indi.xp.coachview.service.SchoolService;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.StringUtils;
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
            if (StringUtils.isNotBlank(school.getSchoolName())) {
                dbSchool.setSchoolName(school.getSchoolName());
            }
            if (StringUtils.isNotBlank(school.getClubId())) {
                dbSchool.setClubId(school.getClubId());
            }
            if (StringUtils.isNotBlank(school.getClubName())) {
                dbSchool.setClubName(school.getClubName());
            }
            if (StringUtils.isNotBlank(school.getAdminId())) {
                dbSchool.setAdminId(school.getAdminId());
            }
            if (StringUtils.isNotBlank(school.getAdminName())) {
                dbSchool.setAdminName(school.getAdminName());
            }
            if (StringUtils.isNotBlank(school.getCounty())) {
                dbSchool.setCounty(school.getCounty());
            }
            if (StringUtils.isNotBlank(school.getProvince())) {
                dbSchool.setProvince(school.getProvince());
            }
            if (StringUtils.isNotBlank(school.getCity())) {
                dbSchool.setCity(school.getCity());
            }
            if (StringUtils.isNotBlank(school.getRegion())) {
                dbSchool.setRegion(school.getRegion());
            }
            return schoolDao.updateSchool(dbSchool);
        }
        return dbSchool;
    }

    @Override
    public void delete(String id) {
        School dbSchool = this.getById(id);
        if (dbSchool != null) {
            schoolDao.delete(id);
        }
    }

    @Override
    public List<School> findListByClubId(String clubId) {
        return schoolDao.findListByClubId(clubId);
    }

}
