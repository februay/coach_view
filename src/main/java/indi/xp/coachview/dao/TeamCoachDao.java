package indi.xp.coachview.dao;

import java.util.List;

import indi.xp.coachview.model.TeamCoach;
import indi.xp.coachview.model.vo.ListItemVo;

public interface TeamCoachDao {

    public TeamCoach getById(String id);

    public List<TeamCoach> findByIdList(List<String> idList);

    public List<TeamCoach> findList();

    public TeamCoach add(TeamCoach teamCoach);

    public TeamCoach update(TeamCoach teamCoach);

    public void delete(String id);

    public void batchDelete(List<String> idList);

    public List<TeamCoach> findListByTeamId(String teamId);

    public List<ListItemVo> findTeamCoachItemList();

    public void deleteByTeamId(String teamId);

}
