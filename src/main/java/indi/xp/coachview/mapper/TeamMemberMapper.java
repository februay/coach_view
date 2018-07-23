package indi.xp.coachview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.TeamMember;

@Mapper
public interface TeamMemberMapper {

    public TeamMember getTeamMemberById(@Param("id") String id);

    public List<TeamMember> getTeamMemberByIdList(@Param("idList") List<String> idList);

    public List<TeamMember> findTeamMemberList();

    public void addTeamMember(@Param("teamMember") TeamMember teamMember);

    public void updateTeamMember(@Param("teamMember") TeamMember teamMember);

    public void delete(@Param("id") String id);

    public void batchDelete(@Param("idList") List<String> idList);

    public List<TeamMember> getByWhere(@Param("paramMap") Map<String, Object[]> paramMap);

    public List<TeamMember> findByWhere(@Param("paramMap") Map<String, Object[]> paramMap);

    public Integer queryCount(@Param("paramMap") Map<String, Object[]> paramMap);

    public void updateByWhere(@Param("updateMap") Map<String, Object> updateMap,
        @Param("paramMap") Map<String, Object[]> paramMap);
}
