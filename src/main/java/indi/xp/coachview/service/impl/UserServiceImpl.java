package indi.xp.coachview.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.UserDao;
import indi.xp.coachview.model.User;
import indi.xp.coachview.service.UserService;

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

}
