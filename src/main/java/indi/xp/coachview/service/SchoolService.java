package indi.xp.coachview.service;

import java.util.List;

import indi.xp.coachview.model.School;
import indi.xp.coachview.model.vo.ListItemVo;

public interface SchoolService {

    public School getById(String id);

    public List<School> findByIdList(List<String> idList);

    public List<School> findList();

    public School add(School school);

    public School update(School school);

    public void delete(String id);

    public List<School> findListByClubId(String clubId);
    
    public List<ListItemVo> findSchoolItemList();

}
