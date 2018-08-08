package indi.xp.coachview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.User;

@Mapper
public interface UserMapper {

    public User getUserByUid(@Param("id") String id, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public User getUserByPhone(@Param("phone") String phone,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<User> getUserByUidList(@Param("uidList") List<String> uidList,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<User> findUserList(@Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void addUser(@Param("user") User user, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void updateUser(@Param("user") User user, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void delete(@Param("id") String id, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void batchDelete(@Param("idList") List<String> idList,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<User> getByWhere(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<User> findByWhere(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public Integer queryCount(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void updateByWhere(@Param("updateMap") Map<String, Object> updateMap,
        @Param("paramMap") Map<String, Object[]> paramMap, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    /**
     * 获取CLUB角色用户有权限的uid列表
     */
    public List<String> findClubUserAuthorizedUidList(@Param("uid") String uid);

}
