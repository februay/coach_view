package indi.xp.coachview.dao;

import java.util.List;

import indi.xp.coachview.model.Team;
import indi.xp.coachview.model.TeamMember;
import indi.xp.coachview.model.vo.ListItemVo;

public interface TeamMemberDao {

    public TeamMember getTeamMemberById(String teamMemberId);

    public List<TeamMember> getTeamMemberByIdList(List<String> idList);

    public List<TeamMember> findTeamMemberList();

    public TeamMember addTeamMember(TeamMember teamMember);

    public TeamMember updateTeamMember(TeamMember teamMember);

    public void delete(String id);

    public void batchDelete(List<String> idList);

    public List<TeamMember> findListByTeamId(String teamId);

    public List<ListItemVo> findTeamMemberItemList();

    public void deleteByTeamId(String teamId);

    public void syncTeamMemberTeamInfo(Team team);

    public TeamMember getByNumber(String teamId, String number);

}
