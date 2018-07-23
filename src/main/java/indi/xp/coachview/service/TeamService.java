package indi.xp.coachview.service;

import java.util.List;

import indi.xp.coachview.model.Team;

public interface TeamService {

    public Team getById(String id);

    public List<Team> findByIdList(List<String> idList);

    public List<Team> findList();

    public Team add(Team team);

    public Team update(Team team);
    
    public void delete(String id);

    public List<Team> findTeamListBySchoolId(String schoolId);

}
