package indi.xp.coachview.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import indi.xp.coachview.dao.ClubDao;
import indi.xp.coachview.model.Club;
import indi.xp.coachview.model.vo.ClubVo;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.service.ClubService;
import indi.xp.coachview.service.SchoolService;
import indi.xp.coachview.service.TeamService;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class ClubServiceImpl implements ClubService {

    @Autowired
    private ClubDao clubDao;

    @Autowired
    private SchoolService schoolService;
    
    @Autowired
    private TeamService teamService;

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
    @Transactional
    public Club update(Club club) {
        Club dbClub = club != null ? clubDao.getClubById(club.getClubId()) : null;
        boolean syncRelatedEntityInfo = false;
        if (dbClub != null) {
            if (club.getClubName() != null && !club.getClubName().equals(dbClub.getClubName())) {
                syncRelatedEntityInfo = true;
                dbClub.setClubName(club.getClubName());
            }
            if (club.getProvince() != null) {
                dbClub.setProvince(club.getProvince());
            }
            if (club.getProvinceName() != null) {
                dbClub.setProvinceName(club.getProvinceName());
            }
            if (club.getCity() != null) {
                dbClub.setCity(club.getCity());
            }
            if (club.getCityName() != null) {
                dbClub.setCityName(club.getCityName());
            }
            if (club.getRegion() != null) {
                dbClub.setRegion(club.getRegion());
            }
            if (club.getRegionName() != null) {
                dbClub.setRegionName(club.getRegionName());
            }
            if (club.getCounty() != null) {
                dbClub.setCounty(club.getCounty());
            }
            if (club.getCountyName() != null) {
                dbClub.setCountyName(club.getCountyName());
            }
            if (club.getStreet() != null) {
                dbClub.setStreet(club.getStreet());
            }
            if (club.getStreetName() != null) {
                dbClub.setStreetName(club.getStreetName());
            }
            if (club.getAdminId() != null) {
                dbClub.setAdminId(club.getAdminId());
            }
            if (club.getAdminName() != null) {
                dbClub.setAdminName(club.getAdminName());
            }
            clubDao.updateClub(dbClub);
            
            if(syncRelatedEntityInfo) {
                schoolService.syncSchoolClubInfo(dbClub);
                teamService.syncTeamClubInfo(dbClub);
            }
        }
        return dbClub;
    }

    @Override
    public void delete(String id) {
        Club dbClub = clubDao.getClubById(id);
        if (dbClub != null) {
            clubDao.delete(id);
            
            // 级联删除school、team
            schoolService.deleteByClubId(id);
            teamService.deleteByClubId(id);
        }
    }

    @Override
    public List<ListItemVo> findClubItemList() {
        return clubDao.findClubItemList();
    }

}
