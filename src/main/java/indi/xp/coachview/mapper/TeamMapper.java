package indi.xp.coachview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.Team;

@Mapper
public interface TeamMapper {

    public Team getTeamById(@Param("id") String id);

    public List<Team> getTeamByIdList(@Param("idList") List<String> idList);

    public List<Team> findTeamList();

    public void addTeam(@Param("team") Team team);

    public void updateTeam(@Param("team") Team team);

    public void delete(@Param("id") String id);

    public void batchDelete(@Param("idList") List<String> idList);

    public List<Team> getByWhere(@Param("paramMap") Map<String, Object[]> paramMap);

    public List<Team> findByWhere(@Param("paramMap") Map<String, Object[]> paramMap);

    public Integer queryCount(@Param("paramMap") Map<String, Object[]> paramMap);

    public void updateByWhere(@Param("updateMap") Map<String, Object> updateMap,
        @Param("paramMap") Map<String, Object[]> paramMap);
}
