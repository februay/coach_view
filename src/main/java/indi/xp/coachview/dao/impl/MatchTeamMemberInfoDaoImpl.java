package indi.xp.coachview.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.druid.util.FnvHash.Constants;
import com.alibaba.fastjson.JSON;

import indi.xp.coachview.common.SysRoleEnum;
import indi.xp.coachview.dao.MatchTeamMemberInfoDao;
import indi.xp.coachview.dao.TeamDao;
import indi.xp.coachview.mapper.MatchTeamMemberInfoMapper;
import indi.xp.coachview.model.MatchTeamMemberInfo;
import indi.xp.coachview.session.SessionConext;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.StringUtils;

@Repository
public class MatchTeamMemberInfoDaoImpl implements MatchTeamMemberInfoDao {

    private static final Logger logger = LoggerFactory.getLogger(MatchTeamMemberInfoDaoImpl.class);

    @Autowired
    private MatchTeamMemberInfoMapper matchTeamMemberInfoMapper;

    @Autowired
    private TeamDao teamDao;

    @Override
    public MatchTeamMemberInfo getById(String id) {
        return matchTeamMemberInfoMapper.getById(id, this.buildAuthFilterMap());
    }

    @Override
    public List<MatchTeamMemberInfo> findByIdList(List<String> idList) {
        return matchTeamMemberInfoMapper.findByIdList(idList, this.buildAuthFilterMap());
    }

    @Override
    public MatchTeamMemberInfo add(MatchTeamMemberInfo matchTeamMemberInfo) {
        matchTeamMemberInfoMapper.add(matchTeamMemberInfo);
        return matchTeamMemberInfo;
    }

    @Override
    public MatchTeamMemberInfo update(MatchTeamMemberInfo matchTeamMemberInfo) {
        matchTeamMemberInfoMapper.update(matchTeamMemberInfo, this.buildAuthFilterMap());
        return matchTeamMemberInfo;
    }

    @Override
    public void delete(String id) {
        if (StringUtils.isNotBlank(id)) {
            matchTeamMemberInfoMapper.delete(id, this.buildAuthFilterMap());
        }
    }

    @Override
    public void batchDelete(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            matchTeamMemberInfoMapper.batchDelete(idList, this.buildAuthFilterMap());
        }
    }

    @Override
    public List<MatchTeamMemberInfo> findListByMatchId(String matchId) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("match_id", new String[] { matchId });
        return matchTeamMemberInfoMapper.findByWhere(paramMap, this.buildAuthFilterMap());
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
                sessionContext.getSessionId(), MatchTeamMemberInfo.class.getSimpleName());
        } else {
            logger.warn("SessionContext is null");
        }
        return authFilterMap;
    }
    
    @Override
    public void deleteByMatchId(String matchId) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("match_id", new String[] { matchId });
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("delete_status", Constants.VALIDATE);
        matchTeamMemberInfoMapper.updateByWhere(updateMap, paramMap, this.buildAuthFilterMap());
    }

}
