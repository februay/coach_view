package indi.xp.coachview.dao;

import java.util.List;

import indi.xp.coachview.model.User;

public interface UserDao {

    public User getUserByUid(String uid);

    public User getUserByPhone(String phone);

    public List<User> findUserListByUidList(List<String> uidList);

    public List<User> findUserList();

    public User addUser(User user);

    public User update(User user);

}
