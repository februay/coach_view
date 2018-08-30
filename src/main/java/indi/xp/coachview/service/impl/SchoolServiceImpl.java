package indi.xp.coachview.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import indi.xp.coachview.dao.SchoolDao;
import indi.xp.coachview.model.Club;
import indi.xp.coachview.model.School;
import indi.xp.coachview.model.User;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.model.vo.ManageOrganizationVo;
import indi.xp.coachview.model.vo.SchoolVo;
import indi.xp.coachview.service.SchoolService;
import indi.xp.coachview.service.TeamService;
import indi.xp.coachview.service.UserService;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    @Override
    public SchoolVo getById(String id, boolean withTeam) {
        SchoolVo schoolVo = null;
        School school = schoolDao.getSchoolById(id);
        if (school != null) {
            schoolVo = new SchoolVo(school);
            if (withTeam) {
                schoolVo.setTeamList(teamService.findTeamListBySchoolId(schoolVo.getSchoolId(), false));
            }
        }
        return schoolVo;
    }

    @Override
    public List<School> findByIdList(List<String> idList) {
        return schoolDao.getSchoolByIdList(idList);
    }

    @Override
    public List<SchoolVo> findList(boolean withTeam) {
        List<SchoolVo> schoolVoList = null;
        List<School> schoolList = schoolDao.findSchoolList();
        if (CollectionUtils.isNotEmpty(schoolList)) {
            schoolVoList = new ArrayList<>();
            for (School school : schoolList) {
                SchoolVo schoolVo = new SchoolVo(school);
                if (withTeam) {
                    schoolVo.setTeamList(teamService.findTeamListBySchoolId(schoolVo.getSchoolId(), false));
                }
                schoolVoList.add(schoolVo);
            }
        }
        return schoolVoList;
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
    @Transactional
    public School update(School school) {
        School dbSchool = school != null ? schoolDao.getSchoolById(school.getSchoolId()) : null;
        boolean syncRelatedEntityInfo = false;
        if (dbSchool != null) {
            if (school.getSchoolName() != null && !school.getSchoolName().equals(dbSchool.getSchoolName())) {
                syncRelatedEntityInfo = true;
                dbSchool.setSchoolName(school.getSchoolName());
            }
            if (school.getSchoolLogo() != null) {
                dbSchool.setSchoolLogo(school.getSchoolLogo());
            }
            if (school.getClubId() != null) {
                dbSchool.setClubId(school.getClubId());
            }
            if (school.getClubName() != null) {
                dbSchool.setClubName(school.getClubName());
            }
            if (school.getAdminId() != null) {
                dbSchool.setAdminId(school.getAdminId());
            }
            if (school.getAdminName() != null) {
                dbSchool.setAdminName(school.getAdminName());
            }
            if (school.getProvince() != null) {
                dbSchool.setProvince(school.getProvince());
            }
            if (school.getProvinceName() != null) {
                dbSchool.setProvinceName(school.getProvinceName());
            }
            if (school.getCity() != null) {
                dbSchool.setCity(school.getCity());
            }
            if (school.getCityName() != null) {
                dbSchool.setCityName(school.getCityName());
            }
            if (school.getRegion() != null) {
                dbSchool.setRegion(school.getRegion());
            }
            if (school.getRegionName() != null) {
                dbSchool.setRegionName(school.getRegionName());
            }
            if (school.getCounty() != null) {
                dbSchool.setCounty(school.getCounty());
            }
            if (school.getCountyName() != null) {
                dbSchool.setCountyName(school.getCountyName());
            }
            if (school.getStreet() != null) {
                dbSchool.setStreet(school.getStreet());
            }
            if (school.getStreetName() != null) {
                dbSchool.setStreetName(school.getStreetName());
            }
            schoolDao.updateSchool(dbSchool);

            if (syncRelatedEntityInfo) {
                teamService.syncTeamSchoolInfo(dbSchool);
                userService.syncUserSchoolInfo(dbSchool);
            }
        }
        return dbSchool;
    }

    @Override
    public void delete(String id) {
        School dbSchool = schoolDao.getSchoolById(id);
        if (dbSchool != null) {
            schoolDao.delete(id);

            // 级联删除team
            teamService.deleteBySchoolId(id);
        }
    }

    @Override
    public List<SchoolVo> findListByClubId(String clubId, boolean withTeam) {
        List<SchoolVo> schoolVoList = null;
        List<School> schoolList = schoolDao.findListByClubId(clubId);
        if (CollectionUtils.isNotEmpty(schoolList)) {
            schoolVoList = new ArrayList<>();
            for (School school : schoolList) {
                SchoolVo schoolVo = new SchoolVo(school);
                if (withTeam) {
                    schoolVo.setTeamList(teamService.findTeamListBySchoolId(schoolVo.getSchoolId(), false));
                }
                schoolVoList.add(schoolVo);
            }
        }
        return schoolVoList;
    }

    @Override
    public List<ListItemVo> findSchoolItemList() {
        return schoolDao.findSchoolItemList();
    }

    @Override
    public void deleteByClubId(String clubId) {
        schoolDao.deleteByClubId(clubId);
    }

    @Override
    public void syncSchoolClubInfo(Club club) {
        schoolDao.syncSchoolClubInfo(club);
    }

    @Override
    public void syncSchoolUserInfo(User user) {
        schoolDao.syncSchoolUserInfo(user);
    }

    @Override
    public List<ManageOrganizationVo> findManageSchoolList(String uid) {
        return schoolDao.findManageSchoolList(uid);
    }

}
