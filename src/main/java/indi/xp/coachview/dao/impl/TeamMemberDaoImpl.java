package indi.xp.coachview.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import indi.xp.coachview.dao.TeamMemberDao;
import indi.xp.coachview.mapper.TeamMemberMapper;
import indi.xp.coachview.model.TeamMember;

@Repository
public class TeamMemberDaoImpl implements TeamMemberDao {

    @Autowired
    private TeamMemberMapper teamMemberMapper;

    @Override
    public TeamMember getTeamMemberById(String teamMemberId) {
        return teamMemberMapper.getTeamMemberById(teamMemberId);
    }

    @Override
    public List<TeamMember> getTeamMemberByIdList(List<String> idList) {
        return teamMemberMapper.getTeamMemberByIdList(idList);
    }

    @Override
    public List<TeamMember> findTeamMemberList() {
        return teamMemberMapper.findTeamMemberList();
    }

    @Override
    public TeamMember addTeamMember(TeamMember teamMember) {
        teamMemberMapper.addTeamMember(teamMember);
        return teamMember;
    }

    @Override
    public TeamMember updateTeamMember(TeamMember teamMember) {
        teamMemberMapper.updateTeamMember(teamMember);
        return teamMember;
    }

}
