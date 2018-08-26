package indi.xp.coachview.dao;

import java.util.List;

import indi.xp.coachview.model.Club;
import indi.xp.coachview.model.School;
import indi.xp.coachview.model.Team;
import indi.xp.coachview.model.User;
import indi.xp.coachview.model.vo.ListItemVo;

public interface UserDao {

    public User getUserByUid(String uid);

    public User getUserByPhone(String phone);

    public List<User> findUserListByUidList(List<String> uidList);

    public List<User> findUserList(String clubId, String schoolId, String teamId);

    public User addUser(User user);

    public User update(User user);

    public List<ListItemVo> findUserItemList(String clubId, String schoolId, String teamId);

    public void syncUserClubInfo(Club club);

    public void syncUserSchoolInfo(School school);

    public void syncUserTeamInfo(Team team);

}
