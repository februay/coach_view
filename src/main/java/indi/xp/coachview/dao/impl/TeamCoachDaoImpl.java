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
import indi.xp.coachview.dao.TeamCoachDao;
import indi.xp.coachview.dao.TeamDao;
import indi.xp.coachview.mapper.TeamCoachMapper;
import indi.xp.coachview.model.TeamCoach;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.session.SessionConext;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.StringUtils;

@Repository
public class TeamCoachDaoImpl implements TeamCoachDao {

    private static final Logger logger = LoggerFactory.getLogger(TeamCoachDaoImpl.class);

    @Autowired
    private TeamCoachMapper teamCoachMapper;

    @Autowired
    private TeamDao teamDao;

    @Override
    public TeamCoach getById(String id) {
        return teamCoachMapper.getById(id, this.buildAuthFilterMap());
    }

    @Override
    public List<TeamCoach> findByIdList(List<String> idList) {
        return teamCoachMapper.findByIdList(idList, this.buildAuthFilterMap());
    }

    @Override
    public List<TeamCoach> findList() {
        return teamCoachMapper.findList(this.buildAuthFilterMap());
    }

    @Override
    public TeamCoach add(TeamCoach teamCoach) {
        teamCoachMapper.add(teamCoach);
        return teamCoach;
    }

    @Override
    public TeamCoach update(TeamCoach teamCoach) {
        teamCoachMapper.update(teamCoach, this.buildAuthFilterMap());
        return teamCoach;
    }

    @Override
    public void delete(String id) {
        if (StringUtils.isNotBlank(id)) {
            teamCoachMapper.delete(id, this.buildAuthFilterMap());
        }
    }

    @Override
    public void batchDelete(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            teamCoachMapper.batchDelete(idList, this.buildAuthFilterMap());
        }
    }

    @Override
    public List<TeamCoach> findListByTeamId(String teamId) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("team_id", new String[] { teamId });
        return teamCoachMapper.findByWhere(paramMap, this.buildAuthFilterMap());
    }

    @Override
    public List<ListItemVo> findTeamCoachItemList() {
        List<TeamCoach> teamCoachList = teamCoachMapper.findList(null);
        List<ListItemVo> itemList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(teamCoachList)) {
            teamCoachList.forEach(coach -> {
                ListItemVo item = new ListItemVo();
                item.setId(coach.getCoachId());
                item.setName(coach.getName());
                item.addExtra("photo", coach.getPhoto());
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
                // 只能访问俱乐部下属球队
                List<String> authorizedTeamIdList = teamDao
                    .findClubUserAuthorizedTeamIdList(sessionContext.getSessionUser().getUid());
                if (CollectionUtils.isNotEmpty(authorizedTeamIdList)) {
                    authFilterMap.put("team_id", authorizedTeamIdList.toArray());
                } else {
                    authFilterMap.put("team_id", new String[] { "" });
                }
            } else if (sessionContext.hasRole(SysRoleEnum.SCHOOL)) {
                // 只能访问学校下属球队
                List<String> authorizedTeamIdList = teamDao
                    .findSchoolUserAuthorizedTeamIdList(sessionContext.getSessionUser().getUid());
                if (CollectionUtils.isNotEmpty(authorizedTeamIdList)) {
                    authFilterMap.put("team_id", authorizedTeamIdList.toArray());
                } else {
                    authFilterMap.put("team_id", new String[] { "" });
                }
            } else if (sessionContext.hasRole(SysRoleEnum.TEAM)) {
                // 只能访问管理的球队
                List<String> authorizedTeamIdList = teamDao
                    .findTeamUserAuthorizedTeamIdList(sessionContext.getSessionUser().getUid());
                if (CollectionUtils.isNotEmpty(authorizedTeamIdList)) {
                    authFilterMap.put("team_id", authorizedTeamIdList.toArray());
                } else {
                    authFilterMap.put("team_id", new String[] { "" });
                }
            } else {
                // 不能访问
                authFilterMap.put("coach_id", new String[] { "" });
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
