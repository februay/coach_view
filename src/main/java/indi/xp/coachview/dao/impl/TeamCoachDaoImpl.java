package indi.xp.coachview.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;

import indi.xp.coachview.dao.TeamCoachDao;
import indi.xp.coachview.mapper.TeamCoachMapper;
import indi.xp.coachview.model.TeamCoach;
import indi.xp.coachview.session.SessionConext;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.StringUtils;

@Repository
public class TeamCoachDaoImpl implements TeamCoachDao {

    private static final Logger logger = LoggerFactory.getLogger(TeamCoachDaoImpl.class);

    @Autowired
    private TeamCoachMapper teamCoachMapper;

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

    private Map<String, Object[]> buildAuthFilterMap() {
        Map<String, Object[]> authFilterMap = new HashMap<>();
        SessionConext sessionContext = SessionConext.getThreadLocalSessionContext();
        if (sessionContext != null) {
            logger.info("SessionContext<{}> : " + JSON.toJSONString(sessionContext), sessionContext.getSessionId());
            logger.info("SessionContext<{}> {} AuthFilterMap: " + JSON.toJSONString(authFilterMap),
                sessionContext.getSessionId(), TeamCoach.class.getSimpleName());
            
            
            
            
        } else {
            logger.warn("SessionContext is null");
        }
        return authFilterMap;
    }

}
