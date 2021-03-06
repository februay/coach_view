package indi.xp.coachview.service;

import java.util.List;

import indi.xp.coachview.model.Club;
import indi.xp.coachview.model.User;
import indi.xp.coachview.model.vo.ClubVo;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.model.vo.ManageOrganizationVo;

public interface ClubService {

    public ClubVo getById(String id, boolean withSchool);

    public List<Club> findByIdList(List<String> idList);

    public List<ClubVo> findList(boolean withSchool);

    public Club add(Club club);

    public Club update(Club club);

    public void delete(String id);

    public List<ListItemVo> findClubItemList();

    public void syncSchoolUserInfo(User user);

    public List<ManageOrganizationVo> findManageClubList(String uid);

}
