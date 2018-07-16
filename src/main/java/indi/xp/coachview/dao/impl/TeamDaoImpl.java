package indi.xp.coachview.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import indi.xp.coachview.dao.TeamDao;
import indi.xp.coachview.mapper.TeamMapper;
import indi.xp.coachview.model.Team;

@Repository
public class TeamDaoImpl implements TeamDao {

    @Autowired
    private TeamMapper teamMapper;

    @Override
    public Team getTeamById(String teamId) {
        return teamMapper.getTeamById(teamId);
    }

    @Override
    public List<Team> getTeamByIdList(List<String> idList) {
        return teamMapper.getTeamByIdList(idList);
    }

    @Override
    public List<Team> findTeamList() {
        return teamMapper.findTeamList();
    }

    @Override
    public Team addTeam(Team team) {
        teamMapper.addTeam(team);
        return team;
    }

    @Override
    public Team updateTeam(Team team) {
        teamMapper.updateTeam(team);
        return team;
    }

}
