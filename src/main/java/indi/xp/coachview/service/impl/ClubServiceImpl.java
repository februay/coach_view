package indi.xp.coachview.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.ClubDao;
import indi.xp.coachview.model.Club;
import indi.xp.coachview.model.vo.ClubVo;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.service.ClubService;
import indi.xp.coachview.service.SchoolService;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.StringUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class ClubServiceImpl implements ClubService {

    @Autowired
    private ClubDao clubDao;

    @Autowired
    private SchoolService schoolService;

    @Override
    public ClubVo getById(String id, boolean withSchool) {
        ClubVo clubVo = null;
        Club club = clubDao.getClubById(id);
        if (club != null) {
            clubVo = new ClubVo(club);
            if (withSchool) {
                clubVo.setSchoolList(schoolService.findListByClubId(clubVo.getClubId(), false));
            }
        }
        return clubVo;
    }

    @Override
    public List<Club> findByIdList(List<String> idList) {
        return clubDao.getClubByIdList(idList);
    }

    @Override
    public List<ClubVo> findList(boolean withSchool) {
        List<ClubVo> clubVoList = null;
        List<Club> clubList = clubDao.findClubList();
        if (CollectionUtils.isNotEmpty(clubList)) {
            clubVoList = new ArrayList<>();
            for (Club club : clubList) {
                ClubVo clubVo = new ClubVo(club);
                if (withSchool) {
                    clubVo.setSchoolList(schoolService.findListByClubId(clubVo.getClubId(), false));
                }
                clubVoList.add(clubVo);
            }
        }
        return clubVoList;
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
        Club dbClub = club != null ? clubDao.getClubById(club.getClubId()) : null;
        if (dbClub != null) {
            if (StringUtils.isNotBlank(club.getClubName())) {
                dbClub.setClubName(club.getClubName());
            }
            if (StringUtils.isNotBlank(club.getProvince())) {
                dbClub.setProvince(club.getProvince());
            }
            if (StringUtils.isNotBlank(club.getProvinceName())) {
                dbClub.setProvinceName(club.getProvinceName());
            }
            if (StringUtils.isNotBlank(club.getCity())) {
                dbClub.setCity(club.getCity());
            }
            if (StringUtils.isNotBlank(club.getCityName())) {
                dbClub.setCityName(club.getCityName());
            }
            if (StringUtils.isNotBlank(club.getRegion())) {
                dbClub.setRegion(club.getRegion());
            }
            if (StringUtils.isNotBlank(club.getRegionName())) {
                dbClub.setRegionName(club.getRegionName());
            }
            if (StringUtils.isNotBlank(club.getCounty())) {
                dbClub.setCounty(club.getCounty());
            }
            if (StringUtils.isNotBlank(club.getCountyName())) {
                dbClub.setCountyName(club.getCountyName());
            }
            if (StringUtils.isNotBlank(club.getStreet())) {
                dbClub.setStreet(club.getStreet());
            }
            if (StringUtils.isNotBlank(club.getStreetName())) {
                dbClub.setStreetName(club.getStreetName());
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
        Club dbClub = clubDao.getClubById(id);
        if (dbClub != null) {
            clubDao.delete(id);
        }
    }

    @Override
    public List<ListItemVo> findClubItemList() {
        return clubDao.findClubItemList();
    }

}
