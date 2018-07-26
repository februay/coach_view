package indi.xp.coachview.service;

import java.util.List;

import indi.xp.coachview.model.TeamCoach;

public interface TeamCoachService {

    public TeamCoach getById(String id);

    public List<TeamCoach> findByIdList(List<String> idList);

    public List<TeamCoach> findList();

    public TeamCoach add(TeamCoach teamCoach);

    public TeamCoach update(TeamCoach teamCoach);

    public void delete(String id);

    public List<TeamCoach> findTeamCoachListByTeamId(String teamId);

}
