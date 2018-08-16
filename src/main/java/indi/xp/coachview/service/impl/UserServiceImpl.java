package indi.xp.coachview.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import indi.xp.coachview.common.BusinessErrorCodeEnum;
import indi.xp.coachview.common.SysRoleEnum;
import indi.xp.coachview.dao.UserDao;
import indi.xp.coachview.model.SysRole;
import indi.xp.coachview.model.SysUserRole;
import indi.xp.coachview.model.User;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.model.vo.UserVo;
import indi.xp.coachview.service.SysRoleService;
import indi.xp.coachview.service.SysUserRoleService;
import indi.xp.coachview.service.UserService;
import indi.xp.coachview.session.SessionConext;
import indi.xp.common.exception.BusinessException;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.StringUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

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
        if(sessionContext != null) {
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

            if (StringUtils.isNotBlank(userVo.getUserName())) {
                user.setUserName(userVo.getUserName());
            }
            if (StringUtils.isNotBlank(userVo.getUserEmail())) {
                user.setUserEmail(userVo.getUserEmail());
            }
            if (StringUtils.isNotBlank(userVo.getUserPassword())) {
                user.setUserPassword(userVo.getUserPassword());
            }
            if (StringUtils.isNotBlank(userVo.getName())) {
                user.setName(userVo.getName());
            }
            if (StringUtils.isNotBlank(userVo.getPhone())) {
                user.setPhone(userVo.getPhone());
            }
            if (StringUtils.isNotBlank(userVo.getCompany())) {
                user.setCompany(userVo.getCompany());
            }
            if (StringUtils.isNotBlank(userVo.getDepartment())) {
                user.setDepartment(userVo.getDepartment());
            }
            if (StringUtils.isNotBlank(userVo.getTitle())) {
                user.setTitle(userVo.getTitle());
            }
            if (StringUtils.isNotBlank(userVo.getClubId())) {
                user.setClubId(userVo.getClubId());
            }
            if (StringUtils.isNotBlank(userVo.getSchoolId())) {
                user.setSchoolId(userVo.getSchoolId());
            }
            if (StringUtils.isNotBlank(userVo.getTeamId())) {
                user.setTeamId(userVo.getTeamId());
            }
            userDao.update(user);

            // 处理roles
            String uid = user.getUid();
            List<String> newRoles = userVo.getRoles();
            if (newRoles != null) {
                List<SysRole> dbRoleList = new ArrayList<SysRole>();
                List<SysUserRole> userRoleList = sysUserRoleService.findListByUid(uid);
                if (CollectionUtils.isNotEmpty(userRoleList)) {
                    List<String> roleIdList = userRoleList.stream().map(userRole -> userRole.getRoleId())
                        .collect(Collectors.toList());
                    dbRoleList = sysRoleService.findListByIdList(roleIdList);
                }

                List<String> addRoleCodeList = new ArrayList<String>();
                List<String> deleteRoleIdList = new ArrayList<String>();
                for (String newRole : newRoles) {
                    boolean isNew = true;
                    if (CollectionUtils.isNotEmpty(dbRoleList)) {
                        for (SysRole role : dbRoleList) {
                            if (role.getCode().equals(newRole)) {
                                isNew = false;
                            }
                        }
                    }
                    if (isNew) {
                        addRoleCodeList.add(newRole);
                    }
                }
                if (CollectionUtils.isNotEmpty(dbRoleList)) {
                    for (SysRole role : dbRoleList) {
                        if (!newRoles.contains(role.getCode())) {
                            deleteRoleIdList.add(role.getRoleId());
                        }
                    }
                }

                if (CollectionUtils.isNotEmpty(addRoleCodeList)) {
                    List<SysRole> addRoleList = sysRoleService.findListByCodeList(addRoleCodeList);
                    if (CollectionUtils.isNotEmpty(addRoleList)) {
                        addRoleList.forEach(role -> {
                            SysUserRole sysUserRole = new SysUserRole();
                            sysUserRole.setRoleId(role.getRoleId());
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
                List<String> roleIdList = userRoleList.stream().map(userRole -> userRole.getRoleId())
                    .collect(Collectors.toList());
                List<SysRole> roleList = sysRoleService.findListByIdList(roleIdList);
                if (CollectionUtils.isNotEmpty(roleList)) {
                    List<String> roleCodeList = roleList.stream().map(role -> role.getCode())
                        .collect(Collectors.toList());
                    userVo.setRoles(roleCodeList);
                }
            }

            return userVo;
        }
        return null;
    }

    private List<UserVo> parseToUserVoList(List<User> userList) {
        List<UserVo> userVoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userList)) {
            for (User user : userList) {
                userVoList.add(this.parseToUserVo(user));
            }
        }
        return userVoList;
    }

    @Override
    public List<ListItemVo> findUserItemList(String clubId, String schoolId, String teamId) {
        return userDao.findUserItemList(clubId, schoolId, teamId );
    }

}
