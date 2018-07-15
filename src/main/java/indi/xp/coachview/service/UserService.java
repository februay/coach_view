package indi.xp.coachview.service;

import java.util.List;

import indi.xp.coachview.model.User;
import indi.xp.coachview.model.vo.UserSignUpVo;

public interface UserService {

    public User getUserByUid(String uid);

    public User getUserByPhone(String phone);

    public List<User> findUserListByUidList(List<String> uidList);

    public List<User> findUserList();

    public User addUser(UserSignUpVo userSignUpVo);

    public User updateUser(UserSignUpVo userSignUpVo);

}
