package indi.xp.coachview.dao;

import java.util.List;

import indi.xp.coachview.model.Club;
import indi.xp.coachview.model.School;
import indi.xp.coachview.model.vo.ListItemVo;

public interface SchoolDao {

    public School getSchoolById(String schoolId);

    public List<School> getSchoolByIdList(List<String> idList);

    public List<School> findSchoolList();

    public School addSchool(School school);

    public School updateSchool(School school);

    public void delete(String id);
    
    public void batchDelete(List<String> idList);

    public List<School> findListByClubId(String clubId);

    public List<ListItemVo> findSchoolItemList();

    public void deleteByClubId(String clubId);

    public void syncSchoolClubInfo(Club club);

}
