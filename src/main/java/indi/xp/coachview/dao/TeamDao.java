package indi.xp.coachview.dao;

import java.util.List;

import indi.xp.coachview.model.Club;
import indi.xp.coachview.model.School;
import indi.xp.coachview.model.Team;
import indi.xp.coachview.model.User;
import indi.xp.coachview.model.vo.ListItemVo;

public interface TeamDao {

    public Team getTeamById(String teamId);

    public List<Team> getTeamByIdList(List<String> idList);

    public List<Team> findTeamList();

    public Team addTeam(Team team);

    public Team updateTeam(Team team);

    public void delete(String id);

    public void batchDelete(List<String> idList);

    public List<Team> findListBySchoolId(String schoolId);

    public List<ListItemVo> findTeamItemList();

    /**
     * 获取CLUB角色用户有权限的teamId列表
     */
    public List<String> findClubUserAuthorizedTeamIdList(String uid);

    /**
     * 获取SCHOOL角色用户有权限的teamId列表
     */
    public List<String> findSchoolUserAuthorizedTeamIdList(String uid);

    /**
     * 获取TEAM角色用户有权限的teamId列表
     */
    public List<String> findTeamUserAuthorizedTeamIdList(String uid);

    public void deleteByClubId(String clubId);

    public void deleteBySchoolId(String schoolId);

    public void syncTeamClubInfo(Club club);

    public void syncTeamSchoolInfo(School school);

    public void syncTeamUserInfo(User user);

}
