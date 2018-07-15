package indi.xp.coachview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.User;

@Mapper
public interface UserMapper {

    public User getUserByUid(@Param("uid") String uid);

    public User getUserByPhone(@Param("phone") String phone);

    public List<User> getUserByUidList(@Param("uidList") List<String> uidList);

    public List<User> findUserList();

    public void addUser(@Param("user") User user);

    public void updateUser(@Param("user") User user);

}
