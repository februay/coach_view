package indi.xp.coachview.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.TeamCoachDao;
import indi.xp.coachview.model.TeamCoach;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.service.TeamCoachService;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class TeamCoachServiceImpl implements TeamCoachService {

    @Autowired
    private TeamCoachDao teamCoachDao;

    @Override
    public TeamCoach getById(String id) {
        return teamCoachDao.getById(id);
    }

    @Override
    public List<TeamCoach> findByIdList(List<String> idList) {
        return teamCoachDao.findByIdList(idList);
    }

    @Override
    public List<TeamCoach> findList() {
        return teamCoachDao.findList();
    }

    @Override
    public TeamCoach add(TeamCoach teamCoach) {
        String currentTime = DateUtils.getDateTime();
        teamCoach.setCoachId(UuidUtils.generateUUID());
        teamCoach.setCreateTime(currentTime);
        teamCoach.setDeleteStatus(false);
        return teamCoachDao.add(teamCoach);
    }

    @Override
    public TeamCoach update(TeamCoach teamCoach) {
        TeamCoach dbTeamCoach = teamCoach != null ? this.getById(teamCoach.getCoachId()) : null;
        if (dbTeamCoach != null) {
            if (teamCoach.getName() != null) {
                dbTeamCoach.setName(teamCoach.getName());
            }
            if (teamCoach.getIdNumber() != null) {
                dbTeamCoach.setIdNumber(teamCoach.getIdNumber());
            }
            if (teamCoach.getPhone() != null) {
                dbTeamCoach.setPhone(teamCoach.getPhone());
            }
            if (teamCoach.getAge() != null) {
                dbTeamCoach.setAge(teamCoach.getAge());
            }
            if (teamCoach.getPhoto() != null) {
                dbTeamCoach.setPhoto(teamCoach.getPhoto());
            }
            if (teamCoach.getTeamId() != null) {
                dbTeamCoach.setTeamId(teamCoach.getTeamId());
            }
            if (teamCoach.getTeamName() != null) {
                dbTeamCoach.setTeamName(teamCoach.getTeamName());
            }
            return teamCoachDao.update(dbTeamCoach);
        }
        return dbTeamCoach;
    }

    @Override
    public void delete(String id) {
        TeamCoach dbTeamCoach = this.getById(id);
        if (dbTeamCoach != null) {
            teamCoachDao.delete(id);
        }
    }

    @Override
    public List<TeamCoach> findTeamCoachListByTeamId(String teamId) {
        return teamCoachDao.findListByTeamId(teamId);
    }

    @Override
    public List<ListItemVo> findTeamCoachItemList() {
        return teamCoachDao.findTeamCoachItemList();
    }

    @Override
    public void deleteByTeamId(String teamId) {
        teamCoachDao.deleteByTeamId(teamId);
    }

}
