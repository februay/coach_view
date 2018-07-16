package indi.xp.coachview.dao;

import java.util.List;

import indi.xp.coachview.model.TeamMember;

public interface TeamMemberDao {

    public TeamMember getTeamMemberById(String teamMemberId);

    public List<TeamMember> getTeamMemberByIdList(List<String> idList);

    public List<TeamMember> findTeamMemberList();

    public TeamMember addTeamMember(TeamMember teamMember);

    public TeamMember updateTeamMember(TeamMember teamMember);

}
