package indi.xp.coachview.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import indi.xp.coachview.common.BusinessErrorCodeEnum;
import indi.xp.coachview.common.SysRoleEnum;
import indi.xp.coachview.dao.UserDao;
import indi.xp.coachview.model.Club;
import indi.xp.coachview.model.School;
import indi.xp.coachview.model.SysRole;
import indi.xp.coachview.model.SysUserRole;
import indi.xp.coachview.model.Team;
import indi.xp.coachview.model.User;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.model.vo.ManageOrganizationVo;
import indi.xp.coachview.model.vo.UserVo;
import indi.xp.coachview.service.ClubService;
import indi.xp.coachview.service.SchoolService;
import indi.xp.coachview.service.SysRoleService;
import indi.xp.coachview.service.SysUserRoleService;
import indi.xp.coachview.service.TeamService;
import indi.xp.coachview.service.UserService;
import indi.xp.coachview.session.SessionConext;
import indi.xp.common.exception.BusinessException;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.StringUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private ClubService clubService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private TeamService teamService;

    @Override
    public UserVo getUserByUid(String uid) {
        User user = userDao.getUserByUid(uid);
        return this.parseToUserVo(user);
    }

    @Override
    public UserVo getUserByPhone(String phone) {
        User user = userDao.getUserByPhone(phone);
        return this.parseToUserVo(user);
    }

    @Override
    public List<UserVo> findUserListByUidList(List<String> uidList) {
        List<User> userList = userDao.findUserListByUidList(uidList);
        return this.parseToUserVoList(userList);
    }

    @Override
    public List<UserVo> findUserList(String clubId, String schoolId, String teamId) {
        List<User> userList = userDao.findUserList(clubId, schoolId, teamId);
        return this.parseToUserVoList(userList);
    }

    @Override
    @Transactional
    public UserVo addUser(UserVo userVo) {
        String currentTime = DateUtils.getDateTime();

        String phoneNumber = userVo.getPhone();
        if (StringUtils.isBlank(phoneNumber)) {
            throw new BusinessException(BusinessErrorCodeEnum.USER_PHONE_NUMBER_IS_BLANK);
        } else if (this.checkUserPhoneExists(phoneNumber, null)) {
            throw new BusinessException(BusinessErrorCodeEnum.USER_EXISTS);
        }

        // 非管理员用户clubId不能为空
        if (!SysRoleEnum.hasAdminRole(userVo.getRoles()) && StringUtils.isBlank(userVo.getClubId())) {
            throw new BusinessException(BusinessErrorCodeEnum.USER_CLUB_ID_IS_NULL);
        }

        userVo.setUid(UuidUtils.generateUUID());
        userVo.setCreateTime(currentTime);
        userVo.setActiveTime(currentTime);

        SessionConext sessionContext = SessionConext.getThreadLocalSessionContext();
        if (sessionContext != null) {
            userVo.setCreatorId(sessionContext.getUid());
        }

        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        user.setDeleteStatus(false);
        user.setStatus("1");
        userDao.addUser(user);

        // 处理roles
        String uid = user.getUid();
        List<String> roleCodeList = userVo.getRoles();
        if (CollectionUtils.isNotEmpty(roleCodeList)) {
            List<SysRole> roleList = sysRoleService.findListByCodeList(roleCodeList);
            if (CollectionUtils.isNotEmpty(roleList)) {
                roleList.forEach(role -> {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setRoleId(role.getRoleId());
                    sysUserRole.setRoleCode(role.getCode());
                    sysUserRole.setUid(uid);
                    sysUserRole.setDeleteStatus(false);
                    sysUserRoleService.add(sysUserRole);
                });
            }
        }

        return userVo;
    }

    @Override
    public boolean checkUserPhoneExists(String phoneNumber, String uid) {
        User user = userDao.getUserByPhone(phoneNumber);
        return user != null && !user.getUid().equals(uid);
    }

    @Override
    @Transactional
    public UserVo updateUser(UserVo userVo) {
        User user = userVo != null ? userDao.getUserByUid(userVo.getUid()) : null;
        if (user != null) {

            String phoneNumber = userVo.getPhone();
            if (phoneNumber != null && StringUtils.isBlank(phoneNumber)) {
                throw new BusinessException(BusinessErrorCodeEnum.USER_PHONE_NUMBER_IS_BLANK);
            } else if (this.checkUserPhoneExists(phoneNumber, user.getUid())) {
                throw new BusinessException(BusinessErrorCodeEnum.USER_EXISTS);
            }

            boolean syncRelatedEntityInfo = false;

            if (userVo.getUserName() != null) {
                user.setUserName(userVo.getUserName());
            }
            if (userVo.getUserEmail() != null) {
                user.setUserEmail(userVo.getUserEmail());
            }
            if (userVo.getUserPassword() != null) {
                user.setUserPassword(userVo.getUserPassword());
            }
            if (userVo.getName() != null) {
                user.setName(userVo.getName());
            }
            if (userVo.getPhone() != null) {
                user.setPhone(userVo.getPhone());
            }
            if (userVo.getCompany() != null) {
                user.setCompany(userVo.getCompany());
            }
            if (userVo.getDepartment() != null) {
                user.setDepartment(userVo.getDepartment());
            }
            if (userVo.getTitle() != null) {
                user.setTitle(userVo.getTitle());
            }
            if (userVo.getClubId() != null) {
                user.setClubId(userVo.getClubId());
            }
            if (userVo.getClubName() != null) {
                user.setClubName(userVo.getClubName());
            }
            if (userVo.getSchoolId() != null) {
                user.setSchoolId(userVo.getSchoolId());
            }
            if (userVo.getSchoolName() != null) {
                user.setSchoolName(userVo.getSchoolName());
            }
            if (userVo.getTeamId() != null) {
                user.setTeamId(userVo.getTeamId());
            }
            if (userVo.getTeamName() != null) {
                user.setTeamName(userVo.getTeamName());
            }
            userDao.update(user);

            if (syncRelatedEntityInfo) {
                clubService.syncSchoolUserInfo(user);
                schoolService.syncSchoolUserInfo(user);
                teamService.syncTeamUserInfo(user);
            }

            // 处理roles
            String uid = user.getUid();
            List<String> newRoles = userVo.getRoles();
            if (newRoles != null) {
                List<String> dbRoleCodeList = new ArrayList<String>();
                List<SysUserRole> dbUserRoleList = sysUserRoleService.findListByUid(uid);
                if (CollectionUtils.isNotEmpty(dbUserRoleList)) {
                    dbRoleCodeList = dbUserRoleList.stream().map(userRole -> userRole.getRoleCode())
                        .collect(Collectors.toList());
                }

                List<String> addRoleCodeList = new ArrayList<String>();
                List<String> deleteRoleIdList = new ArrayList<String>();
                for (String newRole : newRoles) {
                    boolean isNew = true;
                    if (CollectionUtils.isNotEmpty(dbRoleCodeList)) {
                        for (String role : dbRoleCodeList) {
                            if (role.equals(newRole)) {
                                isNew = false;
                            }
                        }
                    }
                    if (isNew) {
                        addRoleCodeList.add(newRole);
                    }
                }
                if (CollectionUtils.isNotEmpty(dbUserRoleList)) {
                    for (SysUserRole dbUserRole : dbUserRoleList) {
                        if (!newRoles.contains(dbUserRole.getRoleCode())) {
                            deleteRoleIdList.add(dbUserRole.getRoleId());
                        }
                    }
                }

                if (CollectionUtils.isNotEmpty(addRoleCodeList)) {
                    List<SysRole> addRoleList = sysRoleService.findListByCodeList(addRoleCodeList);
                    if (CollectionUtils.isNotEmpty(addRoleList)) {
                        addRoleList.forEach(role -> {
                            SysUserRole sysUserRole = new SysUserRole();
                            sysUserRole.setRoleId(role.getRoleId());
                            sysUserRole.setRoleCode(role.getCode());
                            sysUserRole.setUid(uid);
                            sysUserRole.setDeleteStatus(false);
                            sysUserRoleService.add(sysUserRole);
                        });
                    }
                }
                if (CollectionUtils.isNotEmpty(deleteRoleIdList)) {
                    sysUserRoleService.deleteUserRole(uid, deleteRoleIdList);
                }
            }
        }
        return userVo;
    }

    @Override
    public void deleteByUid(String uid) {
        User user = StringUtils.isNotBlank(uid) ? userDao.getUserByUid(uid) : null;
        if (user != null) {
            user.setDeleteStatus(true);
            userDao.update(user);
        }
    }

    private UserVo parseToUserVo(User user) {
        if (user != null) {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);

            String uid = user.getUid();
            List<SysUserRole> userRoleList = sysUserRoleService.findListByUid(uid);
            if (CollectionUtils.isNotEmpty(userRoleList)) {
                List<String> roleCodeList = userRoleList.stream().map(userRole -> userRole.getRoleCode())
                    .collect(Collectors.toList());
                userVo.setRoles(roleCodeList);
            }

            List<ManageOrganizationVo> manageOrganizationList = new ArrayList<>();
            List<ManageOrganizationVo> manageClubList = clubService.findManageClubList(uid);
            List<ManageOrganizationVo> manageSchoolList = schoolService.findManageSchoolList(uid);
            List<ManageOrganizationVo> manageTeamList = teamService.findManageTeamList(uid);
            if (manageClubList != null) {
                manageOrganizationList.addAll(manageClubList);
            }
            if (manageSchoolList != null) {
                manageOrganizationList.addAll(manageSchoolList);
            }
            if (manageTeamList != null) {
                manageOrganizationList.addAll(manageTeamList);
            }
            userVo.setManageOrganizationList(manageOrganizationList);
            userVo.setUserPassword(null);
            return userVo;
        }
        return null;

    }

    private List<UserVo> parseToUserVoList(List<User> userList) {
        long start = System.currentTimeMillis();
        List<UserVo> userVoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userList)) {
            for (User user : userList) {
                userVoList.add(this.parseToUserVo(user));
            }
            long end = System.currentTimeMillis();
            logger.info(">>> parse to user vo list : count = " + userList.size() + ", time=" + (end - start) + "ms");
        }
        return userVoList;
    }

    @Override
    public List<ListItemVo> findUserItemList(String clubId, String schoolId, String teamId) {
        return userDao.findUserItemList(clubId, schoolId, teamId);
    }

    @Override
    public void syncUserClubInfo(Club club) {
        userDao.syncUserClubInfo(club);
    }

    @Override
    public void syncUserSchoolInfo(School school) {
        userDao.syncUserSchoolInfo(school);
    }

    @Override
    public void syncUserTeamInfo(Team team) {
        userDao.syncUserTeamInfo(team);
    }

    @Override
    public User getById(String uid) {
        return userDao.getUserByUid(uid);
    }

}
