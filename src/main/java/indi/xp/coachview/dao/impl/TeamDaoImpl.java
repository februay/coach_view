package indi.xp.coachview.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;

import indi.xp.coachview.common.SysRoleEnum;
import indi.xp.coachview.dao.TeamDao;
import indi.xp.coachview.mapper.TeamMapper;
import indi.xp.coachview.model.Team;
import indi.xp.coachview.model.TeamCoach;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.session.SessionConext;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.StringUtils;

@Repository
public class TeamDaoImpl implements TeamDao {
    
    private static final Logger logger = LoggerFactory.getLogger(TeamDaoImpl.class);

    @Autowired
    private TeamMapper teamMapper;

    @Override
    public Team getTeamById(String teamId) {
        return teamMapper.getTeamById(teamId, this.buildAuthFilterMap());
    }

    @Override
    public List<Team> getTeamByIdList(List<String> idList) {
        return teamMapper.getTeamByIdList(idList, this.buildAuthFilterMap());
    }

    @Override
    public List<Team> findTeamList() {
        return teamMapper.findTeamList( this.buildAuthFilterMap());
    }

    @Override
    public Team addTeam(Team team) {
        teamMapper.addTeam(team);
        return team;
    }

    @Override
    public Team updateTeam(Team team) {
        teamMapper.updateTeam(team, this.buildAuthFilterMap());
        return team;
    }

    @Override
    public void delete(String id) {
        if (StringUtils.isNotBlank(id)) {
            teamMapper.delete(id, this.buildAuthFilterMap());
        }
    }

    @Override
    public void batchDelete(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            teamMapper.batchDelete(idList, this.buildAuthFilterMap());
        }
    }

    @Override
    public List<Team> findListBySchoolId(String schoolId) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("school_id", new String[] { schoolId });
        return teamMapper.findByWhere(paramMap, this.buildAuthFilterMap());
    }

    @Override
    public List<ListItemVo> findTeamItemList() {
        List<Team> teamList = teamMapper.findTeamList(null);
        List<ListItemVo> itemList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(teamList)) {
            teamList.forEach(team -> {
                ListItemVo item = new ListItemVo();
                item.setId(team.getTeamId());
                item.setName(team.getTeamName());
                itemList.add(item);
            });
        }
        return itemList;
    }
    
    private Map<String, Object[]> buildAuthFilterMap() {
        Map<String, Object[]> authFilterMap = new HashMap<>();
        SessionConext sessionContext = SessionConext.getThreadLocalSessionContext();
        if (sessionContext != null && sessionContext.available()) {
            if (sessionContext.hasRole(SysRoleEnum.ADMIN)) {
                // 不限制
            } else if (sessionContext.hasRole(SysRoleEnum.CLUB)) {
                // 只能访问管理员是自己的俱乐部
                authFilterMap.put("admin_id", new String[] { sessionContext.getSessionUser().getUid() });
            } else if (sessionContext.hasRole(SysRoleEnum.SCHOOL)) {
                // 只能访问自己所在的俱乐部

            } else if (sessionContext.hasRole(SysRoleEnum.TEAM)) {
                // 只能访问自己所在的俱乐部
            } else {
                // 不能访问
                authFilterMap.put("club_id", new String[] { "" });
            }

            logger.info("SessionContext<{}> : " + JSON.toJSONString(sessionContext), sessionContext.getSessionId());
            logger.info("SessionContext<{}> {} AuthFilterMap: " + JSON.toJSONString(authFilterMap),
                sessionContext.getSessionId(), TeamCoach.class.getSimpleName());
        } else {
            logger.warn("SessionContext is null");
        }
        return authFilterMap;
    }
}
