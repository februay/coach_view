package indi.xp.coachview.service;

import java.util.List;

import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.model.vo.UserVo;

public interface UserService {

    public UserVo getUserByUid(String uid);

    public UserVo getUserByPhone(String phone);

    public List<UserVo> findUserListByUidList(List<String> uidList);

    public List<UserVo> findUserList();

    public UserVo addUser(UserVo userVo);

    public UserVo updateUser(UserVo userVo);

    public void deleteByUid(String uid);

    public List<ListItemVo> findUserItemList();
}
