package indi.xp.coachview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.User;

@Mapper
public interface UserMapper {

    public User getUserByUid(@Param("id") String id);

    public User getUserByPhone(@Param("phone") String phone);

    public List<User> getUserByUidList(@Param("uidList") List<String> uidList);

    public List<User> findUserList();

    public void addUser(@Param("user") User user);

    public void updateUser(@Param("user") User user);

    public void delete(@Param("id") String id);

    public void batchDelete(@Param("idList") List<String> idList);

    public List<User> getByWhere(@Param("paramMap") Map<String, Object[]> paramMap);

    public List<User> findByWhere(@Param("paramMap") Map<String, Object[]> paramMap);

    public Integer queryCount(@Param("paramMap") Map<String, Object[]> paramMap);

    public void updateByWhere(@Param("updateMap") Map<String, Object> updateMap,
        @Param("paramMap") Map<String, Object[]> paramMap);
    
}
