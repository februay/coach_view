package indi.xp.coachview.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import indi.xp.coachview.dao.TeamMemberDao;
import indi.xp.coachview.mapper.TeamMemberMapper;
import indi.xp.coachview.model.TeamMember;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.StringUtils;

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

    @Override
    public void delete(String id) {
        if (StringUtils.isNotBlank(id)) {
            teamMemberMapper.delete(id);
        }
    }

    @Override
    public void batchDelete(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            teamMemberMapper.batchDelete(idList);
        }
    }

    @Override
    public List<TeamMember> findListByTeamId(String teamId) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("team_id", new String[] { teamId });
        return this.findByWhere(paramMap);
    }

    private List<TeamMember> findByWhere(Map<String, Object[]> paramMap) {
        return teamMemberMapper.findByWhere(paramMap);
    }

}
