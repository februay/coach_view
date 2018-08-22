package indi.xp.coachview.service;

import java.util.List;

import indi.xp.coachview.model.Club;
import indi.xp.coachview.model.vo.ClubVo;
import indi.xp.coachview.model.vo.ListItemVo;

public interface ClubService {

    public ClubVo getById(String id);

    public List<Club> findByIdList(List<String> idList);

    public List<ClubVo> findList(boolean withSchool);

    public Club add(Club club);

    public Club update(Club club);

    public void delete(String id);

    public List<ListItemVo> findClubItemList();

}
