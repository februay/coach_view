package indi.xp.coachview.service;

import java.util.List;

import indi.xp.coachview.model.Club;
import indi.xp.coachview.model.School;
import indi.xp.coachview.model.Team;
import indi.xp.coachview.model.User;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.model.vo.UserVo;

public interface UserService {

    public User getById(String uid);
    
    public UserVo getUserByUid(String uid);

    public UserVo getUserByPhone(String phone);

    public List<UserVo> findUserListByUidList(List<String> uidList);

    public List<UserVo> findUserList(String clubId, String schoolId, String teamId);

    public UserVo addUser(UserVo userVo);

    public UserVo updateUser(UserVo userVo);

    public void deleteByUid(String uid);

    public List<ListItemVo> findUserItemList(String clubId, String schoolId, String teamId);

    public boolean checkUserPhoneExists(String phoneNumber, String uid);

    public void syncUserClubInfo(Club club);

    public void syncUserSchoolInfo(School school);

    public void syncUserTeamInfo(Team team);
}
