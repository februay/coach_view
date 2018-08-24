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
import indi.xp.coachview.dao.ClubDao;
import indi.xp.coachview.mapper.ClubMapper;
import indi.xp.coachview.model.Club;
import indi.xp.coachview.model.User;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.session.SessionConext;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.StringUtils;

@Repository
public class ClubDaoImpl implements ClubDao {

    private static final Logger logger = LoggerFactory.getLogger(ClubDaoImpl.class);

    @Autowired
    private ClubMapper clubMapper;

    @Override
    public Club getClubById(String clubId) {
        return clubMapper.getClubById(clubId, this.buildAuthFilterMap());
    }

    @Override
    public List<Club> getClubByIdList(List<String> idList) {
        return clubMapper.getClubByIdList(idList, this.buildAuthFilterMap());
    }

    @Override
    public List<Club> findClubList() {
        return clubMapper.findClubList(this.buildAuthFilterMap());
    }

    @Override
    public Club addClub(Club club) {
        clubMapper.addClub(club);
        return club;
    }

    @Override
    public Club updateClub(Club club) {
        clubMapper.updateClub(club, this.buildAuthFilterMap());
        return club;
    }

    @Override
    public void delete(String id) {
        if (StringUtils.isNotBlank(id)) {
            clubMapper.delete(id, this.buildAuthFilterMap());
        }
    }

    @Override
    public void batchDelete(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            clubMapper.batchDelete(idList, this.buildAuthFilterMap());
        }
    }

    @Override
    public List<ListItemVo> findClubItemList() {
        List<Club> clubList = clubMapper.findClubList(null);
        List<ListItemVo> itemList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(clubList)) {
            clubList.forEach(club -> {
                ListItemVo item = new ListItemVo();
                item.setId(club.getClubId());
                item.setName(club.getClubName());
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
                List<String> authorizedClbuIdList = this
                    .findClubUserAuthorizedClubIdList(sessionContext.getSessionUser().getUid());
                if (CollectionUtils.isNotEmpty(authorizedClbuIdList)) {
                    authFilterMap.put("club_id", authorizedClbuIdList.toArray());
                } else {
                    authFilterMap.put("club_id", new String[] { "" });
                }
            } else if (sessionContext.hasRole(SysRoleEnum.SCHOOL)) {
                // 只能访问自己所在的俱乐部
                List<String> authorizedClbuIdList = this
                    .findSchoolUserAuthorizedClubIdList(sessionContext.getSessionUser().getUid());
                if (CollectionUtils.isNotEmpty(authorizedClbuIdList)) {
                    authFilterMap.put("club_id", authorizedClbuIdList.toArray());
                } else {
                    authFilterMap.put("club_id", new String[] { "" });
                }
            } else if (sessionContext.hasRole(SysRoleEnum.TEAM)) {
                // 只能访问自己所在的俱乐部
                List<String> authorizedClbuIdList = this
                    .findTeamUserAuthorizedClubIdList(sessionContext.getSessionUser().getUid());
                if (CollectionUtils.isNotEmpty(authorizedClbuIdList)) {
                    authFilterMap.put("club_id", authorizedClbuIdList.toArray());
                } else {
                    authFilterMap.put("club_id", new String[] { "" });
                }
            } else {
                // 不能访问
                authFilterMap.put("club_id", new String[] { "" });
            }

            logger.info("SessionContext<{}> : " + JSON.toJSONString(sessionContext), sessionContext.getSessionId());
            logger.info("SessionContext<{}> {} AuthFilterMap: " + JSON.toJSONString(authFilterMap),
                sessionContext.getSessionId(), Club.class.getSimpleName());
        } else {
            logger.warn("SessionContext is null");
        }
        return authFilterMap;
    }
    
    /**
     * 获取Club角色用户有权限的clubId列表
     */
    private List<String> findClubUserAuthorizedClubIdList(String uid) {
        return clubMapper.findClubUserAuthorizedClubIdList(uid);
    }

    /**
     * 获取SCHOOL角色用户有权限的clubId列表
     */
    private List<String> findSchoolUserAuthorizedClubIdList(String uid) {
        return clubMapper.findSchoolUserAuthorizedClubIdList(uid);
    }

    /**
     * 获取SCHOOL角色用户有权限的clubId列表
     */
    private List<String> findTeamUserAuthorizedClubIdList(String uid) {
        return clubMapper.findTeamUserAuthorizedClubIdList(uid);
    }

    @Override
    public void syncSchoolUserInfo(User user) {
        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("admin_id", new String[] { user.getUid() });
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("admin_name", user.getName());
        clubMapper.updateByWhere(updateMap, paramMap, null);
    }

}
