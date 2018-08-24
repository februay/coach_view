package indi.xp.coachview.service;

import java.util.List;

import indi.xp.coachview.model.Club;
import indi.xp.coachview.model.School;
import indi.xp.coachview.model.User;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.model.vo.SchoolVo;

public interface SchoolService {

    public SchoolVo getById(String id, boolean withTeam);

    public List<School> findByIdList(List<String> idList);

    public List<SchoolVo> findList(boolean withTeam);

    public School add(School school);

    public School update(School school);

    public void delete(String id);

    public List<SchoolVo> findListByClubId(String clubId, boolean withTeam);
    
    public List<ListItemVo> findSchoolItemList();

    public void deleteByClubId(String clubId);

    public void syncSchoolClubInfo(Club club);

    public void syncSchoolUserInfo(User user);

}
