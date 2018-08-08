package indi.xp.coachview.service;

import java.util.List;

import indi.xp.coachview.model.Team;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.model.vo.TeamVo;

public interface TeamService {

    public TeamVo getById(String id);

    public List<Team> findByIdList(List<String> idList);

    public List<TeamVo> findList();

    public Team add(Team team);

    public Team update(Team team);

    public void delete(String id);

    public List<TeamVo> findTeamListBySchoolId(String schoolId);

    public List<ListItemVo> findTeamItemList();

}
