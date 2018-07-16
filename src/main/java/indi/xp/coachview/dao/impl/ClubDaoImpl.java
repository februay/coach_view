package indi.xp.coachview.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import indi.xp.coachview.dao.ClubDao;
import indi.xp.coachview.mapper.ClubMapper;
import indi.xp.coachview.model.Club;

@Repository
public class ClubDaoImpl implements ClubDao {

    @Autowired
    private ClubMapper clubMapper;

    @Override
    public Club getClubById(String clubId) {
        return clubMapper.getClubById(clubId);
    }

    @Override
    public List<Club> getClubByIdList(List<String> idList) {
        return clubMapper.getClubByIdList(idList);
    }

    @Override
    public List<Club> findClubList() {
        return clubMapper.findClubList();
    }

    @Override
    public Club addClub(Club club) {
        clubMapper.addClub(club);
        return club;
    }

    @Override
    public Club updateClub(Club club) {
        clubMapper.updateClub(club);
        return club;
    }

}
