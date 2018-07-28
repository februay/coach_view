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
import indi.xp.coachview.model.TeamCoach;
import indi.xp.coachview.model.User;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.session.SessionConext;
import indi.xp.common.utils.CollectionUtils;

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
    public List<User> findUserList() {
        return userMapper.findUserList(this.buildAuthFilterMap());
    }

    @Override
    public User addUser(User user) {
        userMapper.addUser(user, this.buildAuthFilterMap());
        return user;
    }

    @Override
    public User update(User user) {
        userMapper.updateUser(user, this.buildAuthFilterMap());
        return user;
    }

    @Override
    public List<ListItemVo> findUserItemList() {
        List<User> userList = userMapper.findUserList(null);
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
            } else {
                // 只能访问自己的信息
                authFilterMap.put("uid", new String[] { sessionContext.getSessionUser().getUid() });
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
