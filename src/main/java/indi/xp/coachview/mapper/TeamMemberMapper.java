package indi.xp.coachview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.TeamMember;

@Mapper
public interface TeamMemberMapper {

    public TeamMember getTeamMemberById(@Param("teamMemberId") String teamMemberId);

    public List<TeamMember> getTeamMemberByIdList(@Param("idList") List<String> idList);

    public List<TeamMember> findTeamMemberList();

    public void addTeamMember(@Param("teamMember") TeamMember teamMember);

    public void updateTeamMember(@Param("teamMember") TeamMember teamMember);

}
