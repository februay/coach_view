package indi.xp.coachview.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.TeamDao;
import indi.xp.coachview.model.Team;
import indi.xp.coachview.service.TeamService;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamDao teamDao;

    @Override
    public Team getById(String id) {
        return teamDao.getTeamById(id);
    }

    @Override
    public List<Team> findByIdList(List<String> idList) {
        return teamDao.getTeamByIdList(idList);
    }

    @Override
    public List<Team> findList() {
        return teamDao.findTeamList();
    }

    @Override
    public Team add(Team team) {
        String currentTime = DateUtils.getDateTime();
        team.setTeamId(UuidUtils.generateUUID());
        team.setDeleteStatus(false);
        team.setStatus("1");
        team.setCreateTime(currentTime);
        team.setActiveTime(currentTime);
        return teamDao.addTeam(team);
    }

    @Override
    public Team update(Team team) {
        Team dbTeam = team != null ? this.getById(team.getTeamId()) : null;
        if (dbTeam != null) {
            return teamDao.updateTeam(team);
        }
        return dbTeam;
    }

}
