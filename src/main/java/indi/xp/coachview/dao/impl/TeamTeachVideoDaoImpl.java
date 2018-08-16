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
import indi.xp.coachview.dao.TeamDao;
import indi.xp.coachview.dao.TeamTeachVideoDao;
import indi.xp.coachview.mapper.TeamTeachVideoMapper;
import indi.xp.coachview.model.TeamTeachVideo;
import indi.xp.coachview.session.SessionConext;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.StringUtils;

@Repository
public class TeamTeachVideoDaoImpl implements TeamTeachVideoDao {

    private static final Logger logger = LoggerFactory.getLogger(TeamTeachVideoDaoImpl.class);

    @Autowired
    private TeamTeachVideoMapper teamTeachVideoMapper;

    @Autowired
    private TeamDao teamDao;

    @Override
    public TeamTeachVideo getById(String id) {
        return teamTeachVideoMapper.getById(id, this.buildAuthFilterMap());
    }

    @Override
    public TeamTeachVideo add(TeamTeachVideo teachVideo) {
        teamTeachVideoMapper.add(teachVideo);
        return teachVideo;
    }

    @Override
    public TeamTeachVideo update(TeamTeachVideo teachVideo) {
        teamTeachVideoMapper.update(teachVideo, this.buildAuthFilterMap());
        return teachVideo;
    }

    @Override
    public void delete(String id) {
        if (StringUtils.isNotBlank(id)) {
            teamTeachVideoMapper.delete(id, this.buildAuthFilterMap());
        }
    }

    @Override
    public void batchDelete(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            teamTeachVideoMapper.batchDelete(idList, this.buildAuthFilterMap());
        }
    }

    @Override
    public List<TeamTeachVideo> findListByTeamId(String teamId) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("team_id", new String[] { teamId });
        return teamTeachVideoMapper.findByWhere(paramMap, this.buildAuthFilterMap());
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
                sessionContext.getSessionId(), TeamTeachVideo.class.getSimpleName());
        } else {
            logger.warn("SessionContext is null");
        }
        return authFilterMap;
    }

}
