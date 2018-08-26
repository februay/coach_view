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
import indi.xp.coachview.dao.UserDao;
import indi.xp.coachview.mapper.UserMapper;
import indi.xp.coachview.model.Club;
import indi.xp.coachview.model.School;
import indi.xp.coachview.model.Team;
import indi.xp.coachview.model.User;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.session.SessionConext;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.StringUtils;

@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUid(String uid) {
        return userMapper.getUserByUid(uid, null);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userMapper.getUserByPhone(phone, null);
    }

    @Override
    public List<User> findUserListByUidList(List<String> uidList) {
        return userMapper.getUserByUidList(uidList, this.buildAuthFilterMap());
    }

    @Override
    public List<User> findUserList(String clubId, String schoolId, String teamId) {
        Map<String, Object[]> filterMap = this.buildAuthFilterMap();
        if (filterMap == null) {
            filterMap = new HashMap<>();
        }
        if (StringUtils.isNotBlank(clubId)) {
            filterMap.put("club_id", new String[] { clubId });
        }
        if (StringUtils.isNotBlank(schoolId)) {
            filterMap.put("school_id", new String[] { schoolId });
        }
        if (StringUtils.isNotBlank(teamId)) {
            filterMap.put("team_id", new String[] { teamId });
        }
        return userMapper.findUserList(filterMap);
    }

    @Override
    public User addUser(User user) {
        userMapper.addUser(user, null);
        return user;
    }

    @Override
    public User update(User user) {
        userMapper.updateUser(user, this.buildAuthFilterMap());
        return user;
    }

    @Override
    public List<ListItemVo> findUserItemList(String clubId, String schoolId, String teamId) {
        Map<String, Object[]> filterMap = this.buildAuthFilterMap();
        if (filterMap == null) {
            filterMap = new HashMap<>();
        }
        if (StringUtils.isNotBlank(clubId)) {
            filterMap.put("club_id", new String[] { clubId });
        }
        if (StringUtils.isNotBlank(schoolId)) {
            filterMap.put("school_id", new String[] { schoolId });
        }
        if (StringUtils.isNotBlank(teamId)) {
            filterMap.put("team_id", new String[] { teamId });
        }
        List<User> userList = userMapper.findUserList(filterMap);
        List<ListItemVo> itemList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userList)) {
            userList.forEach(user -> {
                ListItemVo item = new ListItemVo();
                item.setId(user.getUid());
                item.setName(user.getName());
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
                // 只能访问俱乐部下所属人员信息
                List<String> authorizedUidList = this
                    .findClubUserAuthorizedUidList(sessionContext.getSessionUser().getUid());
                if (CollectionUtils.isNotEmpty(authorizedUidList)) {
                    authFilterMap.put("uid", authorizedUidList.toArray());
                } else {
                    authFilterMap.put("uid", new String[] { "" });
                }
            } else {
                // 只能访问自己的信息
                authFilterMap.put("uid", new String[] { sessionContext.getSessionUser().getUid() });
            }

            logger.info("SessionContext<{}> : " + JSON.toJSONString(sessionContext), sessionContext.getSessionId());
            logger.info("SessionContext<{}> {} AuthFilterMap: " + JSON.toJSONString(authFilterMap),
                sessionContext.getSessionId(), User.class.getSimpleName());
        } else {
            logger.warn("SessionContext is null");
        }
        return authFilterMap;
    }

    private List<String> findClubUserAuthorizedUidList(String uid) {
        return userMapper.findClubUserAuthorizedUidList(uid);
    }

    @Override
    public void syncUserClubInfo(Club club) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("club_id", new String[] { club.getClubId() });
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("team_name", club.getClubName());
        userMapper.updateByWhere(updateMap, paramMap, null);
    }

    @Override
    public void syncUserSchoolInfo(School school) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("school_id", new String[] { school.getSchoolId() });
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("school_name", school.getSchoolName());
        userMapper.updateByWhere(updateMap, paramMap, null);
    }

    @Override
    public void syncUserTeamInfo(Team team) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("team_id", new String[] { team.getTeamId() });
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("team_name", team.getTeamName());
        userMapper.updateByWhere(updateMap, paramMap, null);
    }

}
