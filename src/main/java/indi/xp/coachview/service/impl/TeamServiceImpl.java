package indi.xp.coachview.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.TeamDao;
import indi.xp.coachview.model.Club;
import indi.xp.coachview.model.School;
import indi.xp.coachview.model.Team;
import indi.xp.coachview.model.User;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.model.vo.TeamVo;
import indi.xp.coachview.service.TeamCoachService;
import indi.xp.coachview.service.TeamMemberService;
import indi.xp.coachview.service.TeamService;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private TeamMemberService teamMemberService;

    @Autowired
    private TeamCoachService teamCoachService;

    @Override
    public TeamVo getById(String id, boolean withMember) {
        TeamVo teamVo = null;
        Team team = teamDao.getTeamById(id);
        if (team != null) {
            teamVo = new TeamVo(team);
            if (withMember) {
                String teamId = teamVo.getTeamId();
                teamVo.setTeamMemberList(teamMemberService.findTeamMemberListByTeamId(teamId));
                teamVo.setTeamCoachList(teamCoachService.findTeamCoachListByTeamId(teamId));
            }
        }
        return teamVo;
    }

    @Override
    public List<Team> findByIdList(List<String> idList) {
        return teamDao.getTeamByIdList(idList);
    }

    @Override
    public List<TeamVo> findList(boolean withMember) {
        List<TeamVo> teamVoList = null;
        List<Team> teamList = teamDao.findTeamList();
        if (CollectionUtils.isNotEmpty(teamList)) {
            teamVoList = new ArrayList<>();
            for (Team team : teamList) {
                TeamVo teamVo = new TeamVo(team);
                if (withMember) {
                    String teamId = teamVo.getTeamId();
                    teamVo.setTeamMemberList(teamMemberService.findTeamMemberListByTeamId(teamId));
                    teamVo.setTeamCoachList(teamCoachService.findTeamCoachListByTeamId(teamId));
                }
                teamVoList.add(teamVo);
            }
        }
        return teamVoList;
    }

    @Override
    public Team add(Team team) {
        String currentTime = DateUtils.getDateTime();
        team.setTeamId(UuidUtils.generateUUID());
        team.setDeleteStatus(false);
        team.setStatus("1");
        team.setCreateTime(currentTime);
        team.setActiveTime(currentTime);
        return teamDao.addTeam(team);
    }

    @Override
    public Team update(Team team) {
        Team dbTeam = team != null ? teamDao.getTeamById(team.getTeamId()) : null;
        boolean syncRelatedEntityInfo = false;
        if (dbTeam != null) {
            if (team.getTeamName() != null && !team.getTeamName().equals(dbTeam.getTeamName())) {
                syncRelatedEntityInfo = true;
                dbTeam.setTeamName(team.getTeamName());
            }
            if (team.getSchoolId() != null) {
                dbTeam.setSchoolId(team.getSchoolId());
            }
            if (team.getSchoolName() != null) {
                dbTeam.setSchoolName(team.getSchoolName());
            }
            if (team.getClubId() != null) {
                dbTeam.setClubId(team.getClubId());
            }
            if (team.getClubName() != null) {
                dbTeam.setClubName(team.getClubName());
            }
            if (team.getAdminId() != null) {
                dbTeam.setAdminId(team.getAdminId());
            }
            if (team.getAdminName() != null) {
                dbTeam.setAdminName(team.getAdminName());
            }
            if (team.getProvince() != null) {
                dbTeam.setProvince(team.getProvince());
            }
            if (team.getProvinceName() != null) {
                dbTeam.setProvinceName(team.getProvinceName());
            }
            if (team.getCity() != null) {
                dbTeam.setCity(team.getCity());
            }
            if (team.getCityName() != null) {
                dbTeam.setCityName(team.getCityName());
            }
            if (team.getRegion() != null) {
                dbTeam.setRegion(team.getRegion());
            }
            if (team.getRegionName() != null) {
                dbTeam.setRegionName(team.getRegionName());
            }
            if (team.getCounty() != null) {
                dbTeam.setCounty(team.getCounty());
            }
            if (team.getCountyName() != null) {
                dbTeam.setCountyName(team.getCountyName());
            }
            if (team.getStreet() != null) {
                dbTeam.setStreet(team.getStreet());
            }
            if (team.getStreetName() != null) {
                dbTeam.setStreetName(team.getStreetName());
            }
            teamDao.updateTeam(dbTeam);
            
            if(syncRelatedEntityInfo) {
                teamMemberService.syncTeamMemberTeamInfo(dbTeam);
                teamCoachService.syncTeamCoachTeamInfo(dbTeam);
            }
        }
        return dbTeam;
    }

    @Override
    public void delete(String id) {
        Team dbTeam = teamDao.getTeamById(id);
        if (dbTeam != null) {
            teamDao.delete(id);
            
            // 级联删除teamMember、teamCoach
            teamMemberService.deleteByTeamId(id);
            teamCoachService.deleteByTeamId(id);
        }
    }

    @Override
    public List<TeamVo> findTeamListBySchoolId(String schoolId, boolean withMember) {
        List<TeamVo> teamVoList = null;
        List<Team> teamList = teamDao.findListBySchoolId(schoolId);
        if (CollectionUtils.isNotEmpty(teamList)) {
            teamVoList = new ArrayList<>();
            for (Team team : teamList) {
                TeamVo teamVo = new TeamVo(team);
                if (withMember) {
                    String teamId = teamVo.getTeamId();
                    teamVo.setTeamMemberList(teamMemberService.findTeamMemberListByTeamId(teamId));
                    teamVo.setTeamCoachList(teamCoachService.findTeamCoachListByTeamId(teamId));
                }
                teamVoList.add(teamVo);
            }
        }
        return teamVoList;
    }

    @Override
    public List<ListItemVo> findTeamItemList() {
        return teamDao.findTeamItemList();
    }

    @Override
    public void deleteByClubId(String clubId) {
        teamDao.deleteByClubId(clubId);
    }

    @Override
    public void deleteBySchoolId(String schoolId) {
        teamDao.deleteBySchoolId(schoolId);
    }

    @Override
    public void syncTeamClubInfo(Club club) {
        teamDao.syncTeamClubInfo(club);
    }

    @Override
    public void syncTeamSchoolInfo(School school) {
        teamDao.syncTeamSchoolInfo(school);
    }

    @Override
    public void syncTeamUserInfo(User user) {
        teamDao.syncTeamUserInfo(user);
    }

}
