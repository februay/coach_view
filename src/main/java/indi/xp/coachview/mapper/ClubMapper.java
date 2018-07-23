package indi.xp.coachview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.Club;

@Mapper
public interface ClubMapper {

    public Club getClubById(@Param("id") String clubId);

    public List<Club> getClubByIdList(@Param("idList") List<String> idList);

    public List<Club> findClubList();

    public void addClub(@Param("club") Club club);

    public void updateClub(@Param("club") Club club);

    public void delete(@Param("id") String id);

    public void batchDelete(@Param("idList") List<String> idList);

    public List<Club> getByWhere(@Param("paramMap") Map<String, Object[]> paramMap);

    public List<Club> findByWhere(@Param("paramMap") Map<String, Object[]> paramMap);

    public Integer queryCount(@Param("paramMap") Map<String, Object[]> paramMap);

    public void updateByWhere(@Param("updateMap") Map<String, Object> updateMap,
        @Param("paramMap") Map<String, Object[]> paramMap);

}
