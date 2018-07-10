package indi.xp.coachview.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import indi.xp.coachview.dao.UserDao;
import indi.xp.coachview.mapper.UserMapper;
import indi.xp.coachview.model.User;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUid(String uid) {
        return userMapper.getUserByUid(uid);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    @Override
    public List<User> findUserListByUidList(List<String> uidList) {
        return userMapper.getUserByUidList(uidList);
    }

    @Override
    public List<User> findUserList() {
        return userMapper.findUserList();
    }

}
