package indi.xp.coachview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.Team;

@Mapper
public interface TeamMapper {

    public Team getTeamById(@Param("id") String id, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<Team> getTeamByIdList(@Param("idList") List<String> idList,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<Team> findTeamList(@Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void addTeam(@Param("team") Team team);

    public void updateTeam(@Param("team") Team team, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void delete(@Param("id") String id, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void batchDelete(@Param("idList") List<String> idList,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public Team getByWhere(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<Team> findByWhere(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public Integer queryCount(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void updateByWhere(@Param("updateMap") Map<String, Object> updateMap,
        @Param("paramMap") Map<String, Object[]> paramMap, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    /**
     * 获取CLUB角色用户有权限的teamId列表
     */
    public List<String> findClubUserAuthorizedTeamIdList(@Param("uid") String uid);

    /**
     * 获取SCHOOL角色用户有权限的teamId列表
     */
    public List<String> findSchoolUserAuthorizedTeamIdList(@Param("uid") String uid);

    /**
     * 获取TEAM角色用户有权限的teamId列表
     */
    public List<String> findTeamUserAuthorizedTeamIdList(@Param("uid") String uid);
}
