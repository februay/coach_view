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
import indi.xp.coachview.dao.TeamMemberDao;
import indi.xp.coachview.mapper.TeamMemberMapper;
import indi.xp.coachview.model.TeamCoach;
import indi.xp.coachview.model.TeamMember;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.session.SessionConext;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.StringUtils;

@Repository
public class TeamMemberDaoImpl implements TeamMemberDao {

    private static final Logger logger = LoggerFactory.getLogger(TeamMemberDaoImpl.class);

    @Autowired
    private TeamMemberMapper teamMemberMapper;

    @Override
    public TeamMember getTeamMemberById(String teamMemberId) {
        return teamMemberMapper.getTeamMemberById(teamMemberId, this.buildAuthFilterMap());
    }

    @Override
    public List<TeamMember> getTeamMemberByIdList(List<String> idList) {
        return teamMemberMapper.getTeamMemberByIdList(idList, this.buildAuthFilterMap());
    }

    @Override
    public List<TeamMember> findTeamMemberList() {
        return teamMemberMapper.findTeamMemberList(this.buildAuthFilterMap());
    }

    @Override
    public TeamMember addTeamMember(TeamMember teamMember) {
        teamMemberMapper.addTeamMember(teamMember);
        return teamMember;
    }

    @Override
    public TeamMember updateTeamMember(TeamMember teamMember) {
        teamMemberMapper.updateTeamMember(teamMember, this.buildAuthFilterMap());
        return teamMember;
    }

    @Override
    public void delete(String id) {
        if (StringUtils.isNotBlank(id)) {
            teamMemberMapper.delete(id, this.buildAuthFilterMap());
        }
    }

    @Override
    public void batchDelete(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            teamMemberMapper.batchDelete(idList, this.buildAuthFilterMap());
        }
    }

    @Override
    public List<TeamMember> findListByTeamId(String teamId) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("team_id", new String[] { teamId });
        return teamMemberMapper.findByWhere(paramMap, this.buildAuthFilterMap());
    }

    @Override
    public List<ListItemVo> findTeamMemberItemList() {
        List<TeamMember> teamMemberList = teamMemberMapper.findTeamMemberList(null);
        List<ListItemVo> itemList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(teamMemberList)) {
            teamMemberList.forEach(teamMember -> {
                ListItemVo item = new ListItemVo();
                item.setId(teamMember.getMemberId());
                item.setName(teamMember.getTeamName());
                item.addExtra("photo", teamMember.getPhoto());
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
