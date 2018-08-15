package indi.xp.coachview.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;

import indi.xp.coachview.common.SysRoleEnum;
import indi.xp.coachview.dao.MatchDao;
import indi.xp.coachview.dao.TeamDao;
import indi.xp.coachview.mapper.MatchMapper;
import indi.xp.coachview.model.Match;
import indi.xp.coachview.model.TeamCoach;
import indi.xp.coachview.session.SessionConext;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.StringUtils;

@Repository
public class MatchDaoImpl implements MatchDao {

    private static final Logger logger = LoggerFactory.getLogger(MatchDaoImpl.class);

    @Autowired
    private MatchMapper matchMapper;

    @Autowired
    private TeamDao teamDao;

    @Override
    public Match getById(String id) {
        return matchMapper.getById(id, this.buildAuthFilterMap());
    }

    @Override
    public List<Match> findByIdList(List<String> idList) {
        return matchMapper.findByIdList(idList, this.buildAuthFilterMap());
    }

    @Override
    public Match add(Match match) {
        matchMapper.add(match);
        return match;
    }

    @Override
    public Match update(Match match) {
        matchMapper.update(match, this.buildAuthFilterMap());
        return match;
    }

    @Override
    public void delete(String id) {
        if (StringUtils.isNotBlank(id)) {
            matchMapper.delete(id, this.buildAuthFilterMap());
        }
    }

    @Override
    public void batchDelete(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            matchMapper.batchDelete(idList, this.buildAuthFilterMap());
        }
    }

    @Override
    public List<Match> findListByTeamId(String teamId) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("team_id", new String[] { teamId });
        return matchMapper.findByWhere(paramMap, this.buildAuthFilterMap());
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
                authFilterMap.put("team_id", new String[] { "" });
            }

            logger.info("SessionContext<{}> : " + JSON.toJSONString(sessionContext), sessionContext.getSessionId());
            logger.info("SessionContext<{}> {} AuthFilterMap: " + JSON.toJSONString(authFilterMap),
                sessionContext.getSessionId(), TeamCoach.class.getSimpleName());
        } else {
            logger.warn("SessionContext is null");
        }
        return authFilterMap;
    }

    @Override
    public List<Map<String, Object>> statTeamMatchDataInfo(String clubId, String schoolId, String teamId) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("team_id", new String[] { teamId });
        return matchMapper.statTeamMatchDataInfo(clubId, schoolId, teamId, this.buildAuthFilterMap());
    }

}
