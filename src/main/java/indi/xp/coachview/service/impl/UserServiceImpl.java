package indi.xp.coachview.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.UserDao;
import indi.xp.coachview.model.User;
import indi.xp.coachview.model.vo.UserSignUpVo;
import indi.xp.coachview.service.UserService;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.StringUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByUid(String uid) {
        return userDao.getUserByUid(uid);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }

    @Override
    public List<User> findUserListByUidList(List<String> uidList) {
        return userDao.findUserListByUidList(uidList);
    }

    @Override
    public List<User> findUserList() {
        return userDao.findUserList();
    }

    @Override
    public User addUser(UserSignUpVo userSignUpVo) {
        String currentTime = DateUtils.getDateTime();
        User user = new User();
        BeanUtils.copyProperties(userSignUpVo, user);
        user.setUid(UuidUtils.generateUUID());
        user.setDeleteStatus(false);
        user.setStatus("1");
        user.setCreateTime(currentTime);
        user.setActiveTime(currentTime);
        return userDao.addUser(user);
    }

    @Override
    public User updateUser(UserSignUpVo userSignUpVo) {
        User user = userSignUpVo!=null ? this.getUserByUid(userSignUpVo.getUid()) : null;
        if(user != null) {
            if(StringUtils.isNotBlank(userSignUpVo.getUserName())) {
                user.setUserName(userSignUpVo.getUserName());
            }
            if(StringUtils.isNotBlank(userSignUpVo.getUserEmail())) {
                user.setUserEmail(userSignUpVo.getUserEmail());
            }
            if(StringUtils.isNotBlank(userSignUpVo.getUserPassword())) {
                user.setUserPassword(userSignUpVo.getUserPassword());
            }
            if(StringUtils.isNotBlank(userSignUpVo.getName())) {
                user.setName(userSignUpVo.getName());
            }
            if(StringUtils.isNotBlank(userSignUpVo.getPhone())) {
                user.setPhone(userSignUpVo.getPhone());
            }
            if(StringUtils.isNotBlank(userSignUpVo.getCompany())) {
                user.setCompany(userSignUpVo.getCompany());
            }
            if(StringUtils.isNotBlank(userSignUpVo.getDepartment())) {
                user.setDepartment(userSignUpVo.getDepartment());
            }
            if(StringUtils.isNotBlank(userSignUpVo.getTitle())) {
                user.setTitle(userSignUpVo.getTitle());
            }
            return userDao.update(user);
        }
        return user;
    }

}
