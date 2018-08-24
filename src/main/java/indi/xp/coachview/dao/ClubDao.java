package indi.xp.coachview.dao;

import java.util.List;

import indi.xp.coachview.model.Club;
import indi.xp.coachview.model.User;
import indi.xp.coachview.model.vo.ListItemVo;

public interface ClubDao {

    public Club getClubById(String clubId);

    public List<Club> getClubByIdList(List<String> idList);

    public List<Club> findClubList();

    public Club addClub(Club club);

    public Club updateClub(Club club);

    public void delete(String id);

    public void batchDelete(List<String> idList);

    public List<ListItemVo> findClubItemList();

    public void syncSchoolUserInfo(User user);

}
