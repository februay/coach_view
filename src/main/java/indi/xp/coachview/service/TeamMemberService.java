package indi.xp.coachview.service;

import java.util.List;

import indi.xp.coachview.model.Team;
import indi.xp.coachview.model.TeamMember;
import indi.xp.coachview.model.vo.ListItemVo;

public interface TeamMemberService {

    public TeamMember getById(String id);

    public List<TeamMember> findByIdList(List<String> idList);

    public List<TeamMember> findList();

    public TeamMember add(TeamMember teamMember);

    public TeamMember update(TeamMember teamMember);

    public void delete(String id);

    public List<TeamMember> findTeamMemberListByTeamId(String teamId);
    
    public List<ListItemVo> findTeamMemberItemList();

    public void deleteByTeamId(String teamId);

    public void syncTeamMemberTeamInfo(Team team);

    public boolean checkTeamMemberExists(TeamMember teamMember);

    public TeamMember getByNumber(String teamId, String number);

}
