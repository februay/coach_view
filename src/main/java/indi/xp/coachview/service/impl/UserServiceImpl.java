package indi.xp.coachview.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.UserDao;
import indi.xp.coachview.model.User;
import indi.xp.coachview.model.vo.UserVo;
import indi.xp.coachview.service.UserService;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.StringUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

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
    public List<UserVo> findUserList() {
        List<User> userList = userDao.findUserList();
        return this.parseToUserVoList(userList);
    }

    @Override
    public UserVo addUser(UserVo userVo) {
        String currentTime = DateUtils.getDateTime();

        userVo.setUid(UuidUtils.generateUUID());
        userVo.setCreateTime(currentTime);
        userVo.setActiveTime(currentTime);

        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        user.setDeleteStatus(false);
        user.setStatus("1");
        userDao.addUser(user);
        
        return userVo;
    }

    @Override
    public UserVo updateUser(UserVo userVo) {
        User user = userVo != null ? userDao.getUserByUid(userVo.getUid()) : null;
        if (user != null) {
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
            userDao.update(user);
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

            // TODO： 处理roles
            
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

}
