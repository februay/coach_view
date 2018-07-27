package indi.xp.coachview.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.ClubDao;
import indi.xp.coachview.model.Club;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.service.ClubService;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.StringUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class ClubServiceImpl implements ClubService {

    @Autowired
    private ClubDao clubDao;

    @Override
    public Club getById(String id) {
        return clubDao.getClubById(id);
    }

    @Override
    public List<Club> findByIdList(List<String> idList) {
        return clubDao.getClubByIdList(idList);
    }

    @Override
    public List<Club> findList() {
        return clubDao.findClubList();
    }

    @Override
    public Club add(Club club) {
        String currentTime = DateUtils.getDateTime();
        club.setClubId(UuidUtils.generateUUID());
        club.setDeleteStatus(false);
        club.setStatus("1");
        club.setCreateTime(currentTime);
        club.setActiveTime(currentTime);
        return clubDao.addClub(club);
    }

    @Override
    public Club update(Club club) {
        Club dbClub = club != null ? this.getById(club.getClubId()) : null;
        if (dbClub != null) {
            if (StringUtils.isNotBlank(club.getClubName())) {
                dbClub.setClubName(club.getClubName());
            }
            if (StringUtils.isNotBlank(club.getCounty())) {
                dbClub.setCounty(club.getCounty());
            }
            if (StringUtils.isNotBlank(club.getProvince())) {
                dbClub.setProvince(club.getProvince());
            }
            if (StringUtils.isNotBlank(club.getCity())) {
                dbClub.setCity(club.getCity());
            }
            if (StringUtils.isNotBlank(club.getRegion())) {
                dbClub.setRegion(club.getRegion());
            }
            if (StringUtils.isNotBlank(club.getAdminId())) {
                dbClub.setAdminId(club.getAdminId());
            }
            if (StringUtils.isNotBlank(club.getAdminName())) {
                dbClub.setAdminName(club.getAdminName());
            }
            return clubDao.updateClub(dbClub);
        }
        return dbClub;
    }

    @Override
    public void delete(String id) {
        Club dbClub = this.getById(id);
        if (dbClub != null) {
            clubDao.delete(id);
        }
    }

    @Override
    public List<ListItemVo> findClubItemList() {
        return clubDao.findClubItemList();
    }

}
