package indi.xp.coachview.dao;

import java.util.List;

import indi.xp.coachview.model.Team;

public interface TeamDao {

    public Team getTeamById(String teamId);

    public List<Team> getTeamByIdList(List<String> idList);

    public List<Team> findTeamList();

    public Team addTeam(Team team);

    public Team updateTeam(Team team);

    public void delete(String id);

    public void batchDelete(List<String> idList);

    public List<Team> findListBySchoolId(String schoolId);

}
