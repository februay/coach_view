package indi.xp.coachview.service;

import java.util.List;

import indi.xp.coachview.model.School;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.model.vo.SchoolVo;

public interface SchoolService {

    public SchoolVo getById(String id);

    public List<School> findByIdList(List<String> idList);

    public List<SchoolVo> findList();

    public School add(School school);

    public School update(School school);

    public void delete(String id);

    public List<SchoolVo> findListByClubId(String clubId);
    
    public List<ListItemVo> findSchoolItemList();

}
