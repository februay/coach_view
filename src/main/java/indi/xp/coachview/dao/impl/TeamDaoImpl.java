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

import indi.xp.coachview.common.Constants;
import indi.xp.coachview.common.SysRoleEnum;
import indi.xp.coachview.dao.TeamDao;
import indi.xp.coachview.mapper.TeamMapper;
import indi.xp.coachview.model.Club;
import indi.xp.coachview.model.School;
import indi.xp.coachview.model.Team;
import indi.xp.coachview.model.User;
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
        return teamMapper.findTeamList(this.buildAuthFilterMap());
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
                // 只能访问俱乐部下属球队
                List<String> authorizedTeamIdList = this
                    .findClubUserAuthorizedTeamIdList(sessionContext.getSessionUser().getUid());
                if (CollectionUtils.isNotEmpty(authorizedTeamIdList)) {
                    authFilterMap.put("team_id", authorizedTeamIdList.toArray());
                } else {
                    authFilterMap.put("team_id", new String[] { "" });
                }
            } else if (sessionContext.hasRole(SysRoleEnum.SCHOOL)) {
                // 只能访问学校下属球队
                List<String> authorizedTeamIdList = this
                    .findSchoolUserAuthorizedTeamIdList(sessionContext.getSessionUser().getUid());
                if (CollectionUtils.isNotEmpty(authorizedTeamIdList)) {
                    authFilterMap.put("team_id", authorizedTeamIdList.toArray());
                } else {
                    authFilterMap.put("team_id", new String[] { "" });
                }
            } else if (sessionContext.hasRole(SysRoleEnum.TEAM)) {
                // 只能访问自己所在的俱乐部
                List<String> authorizedTeamIdList = this
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
                sessionContext.getSessionId(), Team.class.getSimpleName());
        } else {
            logger.warn("SessionContext is null");
        }
        return authFilterMap;
    }

    /**
     * 获取CLUB角色用户有权限的teamId列表
     */
    @Override
    public List<String> findClubUserAuthorizedTeamIdList(String uid) {
        return teamMapper.findClubUserAuthorizedTeamIdList(uid);
    }

    /**
     * 获取SCHOOL角色用户有权限的teamId列表
     */
    @Override
    public List<String> findSchoolUserAuthorizedTeamIdList(String uid) {
        return teamMapper.findSchoolUserAuthorizedTeamIdList(uid);
    }

    /**
     * 获取TEAM角色用户有权限的teamId列表
     */
    @Override
    public List<String> findTeamUserAuthorizedTeamIdList(String uid) {
        return teamMapper.findTeamUserAuthorizedTeamIdList(uid);
    }

    @Override
    public void deleteByClubId(String clubId) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("club_id", new String[] { clubId });
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("delete_status", Constants.VALIDATE);
        teamMapper.updateByWhere(updateMap, paramMap, this.buildAuthFilterMap());
    }

    @Override
    public void deleteBySchoolId(String schoolId) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("school_id", new String[] { schoolId });
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("delete_status", Constants.VALIDATE);
        teamMapper.updateByWhere(updateMap, paramMap, this.buildAuthFilterMap());
    }

    @Override
    public void syncTeamClubInfo(Club club) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("club_id", new String[] { club.getClubId() });
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("club_name", club.getClubName());
        teamMapper.updateByWhere(updateMap, paramMap, null);
    }

    @Override
    public void syncTeamSchoolInfo(School school) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("school_id", new String[] { school.getSchoolId() });
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("school_name", school.getSchoolName());
        teamMapper.updateByWhere(updateMap, paramMap, null);
    }

    @Override
    public void syncTeamUserInfo(User user) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("admin_id", new String[] { user.getUid() });
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("admin_name", user.getName());
        teamMapper.updateByWhere(updateMap, paramMap, null);
    }
}
