package indi.xp.coachview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.School;

@Mapper
public interface SchoolMapper {

    public School getSchoolById(@Param("id") String id, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<School> getSchoolByIdList(@Param("idList") List<String> idList,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<School> findSchoolList(@Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void addSchool(@Param("school") School school);

    public void updateSchool(@Param("school") School school,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void delete(@Param("id") String id, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public void batchDelete(@Param("idList") List<String> idList,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public School getByWhere(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public List<School> findByWhere(@Param("paramMap") Map<String, Object[]> paramMap,
        @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    public Integer queryCount(@Param("paramMap") Map<String, Object[]> paramMap);

    public void updateByWhere(@Param("updateMap") Map<String, Object> updateMap,
        @Param("paramMap") Map<String, Object[]> paramMap, @Param("authFilterMap") Map<String, Object[]> authFilterMap);

    /**
     * 获取CLUB角色用户有权限的schoolId列表
     */
    public List<String> findClubUserAuthorizedSchoolIdList(@Param("uid") String uid);

    /**
     * 获取TEAM角色用户有权限的schoolId列表
     */
    public List<String> findTeamUserAuthorizedSchoolIdList(@Param("uid") String uid);

}
