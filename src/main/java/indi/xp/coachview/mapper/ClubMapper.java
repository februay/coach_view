package indi.xp.coachview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.Club;

@Mapper
public interface ClubMapper {

    public Club getClubById(@Param("id") String clubId, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<Club> getClubByIdList(@Param("idList") List<String> idList,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<Club> findClubList(@Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void addClub(@Param("club") Club club);

    public void updateClub(@Param("club") Club club, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void delete(@Param("id") String id, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void batchDelete(@Param("idList") List<String> idList,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public Club getByWhere(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<Club> findByWhere(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public Integer queryCount(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void updateByWhere(@Param("updateMap") Map<String, Object> updateMap,
        @Param("paramMap") Map<String, Object[]> paramMap, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    /**
     * 获取SCHOOL角色用户有权限的clubId列表
     */
    public List<String> findSchoolUserAuthorizedClubIdList(@Param("uid") String uid);

    /**
     * 获取SCHOOL角色用户有权限的clubId列表
     */
    public List<String> findTeamUserAuthorizedClubIdList(@Param("uid") String uid);

}
