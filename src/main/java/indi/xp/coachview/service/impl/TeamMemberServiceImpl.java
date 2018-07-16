package indi.xp.coachview.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.TeamMemberDao;
import indi.xp.coachview.model.TeamMember;
import indi.xp.coachview.service.TeamMemberService;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class TeamMemberServiceImpl implements TeamMemberService {

    @Autowired
    private TeamMemberDao teamMemberDao;

    @Override
    public TeamMember getById(String id) {
        return teamMemberDao.getTeamMemberById(id);
    }

    @Override
    public List<TeamMember> findByIdList(List<String> idList) {
        return teamMemberDao.getTeamMemberByIdList(idList);
    }

    @Override
    public List<TeamMember> findList() {
        return teamMemberDao.findTeamMemberList();
    }

    @Override
    public TeamMember add(TeamMember teamMember) {
        String currentTime = DateUtils.getDateTime();
        teamMember.setMemberId(UuidUtils.generateUUID());
        teamMember.setCreateTime(currentTime);
        teamMember.setDeleteStatus(false);
        return teamMemberDao.addTeamMember(teamMember);
    }

    @Override
    public TeamMember update(TeamMember teamMember) {
        TeamMember dbTeamMember = teamMember != null ? this.getById(teamMember.getMemberId()) : null;
        if (dbTeamMember != null) {
            return teamMemberDao.updateTeamMember(teamMember);
        }
        return dbTeamMember;
    }

}
