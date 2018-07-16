package indi.xp.coachview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.Club;

@Mapper
public interface ClubMapper {

    public Club getClubById(@Param("clubId") String clubId);

    public List<Club> getClubByIdList(@Param("idList") List<String> idList);

    public List<Club> findClubList();

    public void addClub(@Param("club") Club club);

    public void updateClub(@Param("club") Club club);

}
