package indi.xp.coachview.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import indi.xp.coachview.dao.TeamDao;
import indi.xp.coachview.mapper.TeamMapper;
import indi.xp.coachview.model.Team;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.StringUtils;

@Repository
public class TeamDaoImpl implements TeamDao {

    @Autowired
    private TeamMapper teamMapper;

    @Override
    public Team getTeamById(String teamId) {
        return teamMapper.getTeamById(teamId);
    }

    @Override
    public List<Team> getTeamByIdList(List<String> idList) {
        return teamMapper.getTeamByIdList(idList);
    }

    @Override
    public List<Team> findTeamList() {
        return teamMapper.findTeamList();
    }

    @Override
    public Team addTeam(Team team) {
        teamMapper.addTeam(team);
        return team;
    }

    @Override
    public Team updateTeam(Team team) {
        teamMapper.updateTeam(team);
        return team;
    }

    @Override
    public void delete(String id) {
        if (StringUtils.isNotBlank(id)) {
            teamMapper.delete(id);
        }
    }

    @Override
    public void batchDelete(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            teamMapper.batchDelete(idList);
        }
    }

    @Override
    public List<Team> findListBySchoolId(String schoolId) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("school_id", new String[] { schoolId });
        return this.findByWhere(paramMap);
    }

    private List<Team> findByWhere(Map<String, Object[]> paramMap) {
        return teamMapper.findByWhere(paramMap);
    }
}
