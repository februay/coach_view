package indi.xp.coachview.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;

import indi.xp.coachview.common.Constants;
import indi.xp.coachview.common.SysRoleEnum;
import indi.xp.coachview.dao.SchoolDao;
import indi.xp.coachview.mapper.SchoolMapper;
import indi.xp.coachview.model.Club;
import indi.xp.coachview.model.School;
import indi.xp.coachview.model.User;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.model.vo.ManageOrganizationVo;
import indi.xp.coachview.session.SessionConext;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.StringUtils;

@Repository
public class SchoolDaoImpl implements SchoolDao {

    private static final Logger logger = LoggerFactory.getLogger(SchoolDaoImpl.class);

    @Autowired
    private SchoolMapper schoolMapper;

    @Override
    public School getSchoolById(String schoolId) {
        return schoolMapper.getSchoolById(schoolId, this.buildAuthFilterMap());
    }

    @Override
    public List<School> getSchoolByIdList(List<String> idList) {
        return schoolMapper.getSchoolByIdList(idList, this.buildAuthFilterMap());
    }

    @Override
    public List<School> findSchoolList() {
        return schoolMapper.findSchoolList(this.buildAuthFilterMap());
    }

    @Override
    public School addSchool(School school) {
        schoolMapper.addSchool(school);
        return school;
    }

    @Override
    public School updateSchool(School school) {
        schoolMapper.updateSchool(school, this.buildAuthFilterMap());
        return school;
    }

    @Override
    public void delete(String id) {
        if (StringUtils.isNotBlank(id)) {
            schoolMapper.delete(id, this.buildAuthFilterMap());
        }
    }

    @Override
    public void batchDelete(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            schoolMapper.batchDelete(idList, this.buildAuthFilterMap());
        }
    }

    @Override
    public List<School> findListByClubId(String clubId) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("club_id", new String[] { clubId });
        return this.findByWhere(paramMap);
    }

    private List<School> findByWhere(Map<String, Object[]> paramMap) {
        return schoolMapper.findByWhere(paramMap, this.buildAuthFilterMap());
    }

    @Override
    public List<ListItemVo> findSchoolItemList() {
        List<School> schoolList = schoolMapper.findSchoolList(null);
        List<ListItemVo> itemList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(schoolList)) {
            schoolList.forEach(school -> {
                ListItemVo item = new ListItemVo();
                item.setId(school.getSchoolId());
                item.setName(school.getSchoolName());
                itemList.add(item);
            });
        }
        return itemList;
    }

    private Map<String, Object[]> buildAuthFilterMap() {
        Map<String, Object[]> authFilterMap = new HashMap<>();
        SessionConext sessionContext = SessionConext.getThreadLocalSessionContext();
        if (sessionContext != null && sessionContext.available()) {
            if (sessionContext.hasRole(SysRoleEnum.ADMIN)) {
                // 不限制
            } else if (sessionContext.hasRole(SysRoleEnum.CLUB)) {
                // 只能访问俱乐部下属学校
                List<String> authorizedSchoolIdList = this
                    .findClubUserAuthorizedSchoolIdList(sessionContext.getSessionUser().getUid());
                if (CollectionUtils.isNotEmpty(authorizedSchoolIdList)) {
                    authFilterMap.put("school_id", authorizedSchoolIdList.toArray());
                } else {
                    authFilterMap.put("school_id", new String[] { "" });
                }
            } else if (sessionContext.hasRole(SysRoleEnum.SCHOOL)) {
                // 只能访问管理员是自己的学校
                List<String> authorizedSchoolIdList = this
                    .findSchoolUserAuthorizedSchoolIdList(sessionContext.getSessionUser().getUid());
                if (CollectionUtils.isNotEmpty(authorizedSchoolIdList)) {
                    authFilterMap.put("school_id", authorizedSchoolIdList.toArray());
                } else {
                    authFilterMap.put("school_id", new String[] { "" });
                }
            } else if (sessionContext.hasRole(SysRoleEnum.TEAM)) {
                // 只能访问自己所在的学校
                List<String> authorizedSchoolIdList = this
                    .findTeamUserAuthorizedSchoolIdList(sessionContext.getSessionUser().getUid());
                if (CollectionUtils.isNotEmpty(authorizedSchoolIdList)) {
                    authFilterMap.put("school_id", authorizedSchoolIdList.toArray());
                } else {
                    authFilterMap.put("school_id", new String[] { "" });
                }
            } else {
                // 不能访问
                authFilterMap.put("school_id", new String[] { "" });
            }

            logger.info("SessionContext<{}> : " + JSON.toJSONString(sessionContext), sessionContext.getSessionId());
            logger.info("SessionContext<{}> {} AuthFilterMap: " + JSON.toJSONString(authFilterMap),
                sessionContext.getSessionId(), School.class.getSimpleName());
        } else {
            logger.warn("SessionContext is null");
        }
        return authFilterMap;
    }

    /**
     * 获取CLUB角色用户有权限的schoolId列表
     */
    private List<String> findClubUserAuthorizedSchoolIdList(String uid) {
        return schoolMapper.findClubUserAuthorizedSchoolIdList(uid);
    }

    /**
     * 获取School角色用户有权限的schoolId列表
     */
    private List<String> findSchoolUserAuthorizedSchoolIdList(String uid) {
        return schoolMapper.findSchoolUserAuthorizedSchoolIdList(uid);
    }

    /**
     * 获取TEAM角色用户有权限的schoolId列表
     */
    private List<String> findTeamUserAuthorizedSchoolIdList(String uid) {
        return schoolMapper.findTeamUserAuthorizedSchoolIdList(uid);
    }

    @Override
    public void deleteByClubId(String clubId) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("club_id", new String[] { clubId });
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("delete_status", Constants.VALIDATE);
        schoolMapper.updateByWhere(updateMap, paramMap, null);
    }

    @Override
    public void syncSchoolClubInfo(Club club) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("club_id", new String[] { club.getClubId() });
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("club_name", club.getClubName());
        schoolMapper.updateByWhere(updateMap, paramMap, null);
    }

    @Override
    public void syncSchoolUserInfo(User user) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("admin_id", new String[] { user.getUid() });
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("admin_name", user.getName());
        schoolMapper.updateByWhere(updateMap, paramMap, null);
    }

    @Override
    public List<ManageOrganizationVo> findManageSchoolList(String uid) {
        List<ManageOrganizationVo> manageSchoolList = null;
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        if (StringUtils.isNotBlank(uid)) {
            paramMap.put("admin_id", new String[] { uid });
        }
        List<School> schoolList = schoolMapper.findByWhere(paramMap, null);
        if (CollectionUtils.isNotEmpty(schoolList)) {
            manageSchoolList = new ArrayList<>();
            for (School school : schoolList) {
                ManageOrganizationVo manageSchool = new ManageOrganizationVo();
                manageSchool.setType(ManageOrganizationVo.TYPE_SCHOOL);
                manageSchool.setId(school.getSchoolId());
                manageSchool.setName(school.getSchoolName());
                manageSchoolList.add(manageSchool);
            }
        }
        return manageSchoolList;
    }

}
