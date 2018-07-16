package indi.xp.coachview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.Team;

@Mapper
public interface TeamMapper {

    public Team getTeamById(@Param("teamId") String teamId);

    public List<Team> getTeamByIdList(@Param("idList") List<String> idList);

    public List<Team> findTeamList();

    public void addTeam(@Param("team") Team team);

    public void updateTeam(@Param("team") Team team);

}
