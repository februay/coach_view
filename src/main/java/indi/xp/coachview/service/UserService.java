package indi.xp.coachview.service;

import java.util.List;

import indi.xp.coachview.model.User;

public interface UserService {

    public User getUserByUid(String uid);

    public User getUserByPhone(String phone);

    public List<User> findUserListByUidList(List<String> uidList);

    public List<User> findUserList();

}
