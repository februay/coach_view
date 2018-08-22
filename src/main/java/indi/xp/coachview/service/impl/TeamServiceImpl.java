package indi.xp.coachview.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.TeamDao;
import indi.xp.coachview.model.Team;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.model.vo.TeamVo;
import indi.xp.coachview.service.TeamCoachService;
import indi.xp.coachview.service.TeamMemberService;
import indi.xp.coachview.service.TeamService;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.StringUtils;
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
    public TeamVo getById(String id) {
        TeamVo teamVo = null;
        Team team = teamDao.getTeamById(id);
        if (team != null) {
            teamVo = new TeamVo(team);
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
        Team dbTeam = team != null ? this.getById(team.getTeamId()) : null;
        if (dbTeam != null) {
            if (StringUtils.isNotBlank(team.getTeamName())) {
                dbTeam.setTeamName(team.getTeamName());
            }
            if (StringUtils.isNotBlank(team.getSchoolId())) {
                dbTeam.setSchoolId(team.getSchoolId());
            }
            if (StringUtils.isNotBlank(team.getSchoolName())) {
                dbTeam.setSchoolName(team.getSchoolName());
            }
            if (StringUtils.isNotBlank(team.getAdminId())) {
                dbTeam.setAdminId(team.getAdminId());
            }
            if (StringUtils.isNotBlank(team.getAdminName())) {
                dbTeam.setAdminName(team.getAdminName());
            }
            if (StringUtils.isNotBlank(team.getProvince())) {
                dbTeam.setProvince(team.getProvince());
            }
            if (StringUtils.isNotBlank(team.getProvinceName())) {
                dbTeam.setProvinceName(team.getProvinceName());
            }
            if (StringUtils.isNotBlank(team.getCity())) {
                dbTeam.setCity(team.getCity());
            }
            if (StringUtils.isNotBlank(team.getCityName())) {
                dbTeam.setCityName(team.getCityName());
            }
            if (StringUtils.isNotBlank(team.getRegion())) {
                dbTeam.setRegion(team.getRegion());
            }
            if (StringUtils.isNotBlank(team.getRegionName())) {
                dbTeam.setRegionName(team.getRegionName());
            }
            if (StringUtils.isNotBlank(team.getCounty())) {
                dbTeam.setCounty(team.getCounty());
            }
            if (StringUtils.isNotBlank(team.getCountyName())) {
                dbTeam.setCountyName(team.getCountyName());
            }
            if (StringUtils.isNotBlank(team.getStreet())) {
                dbTeam.setStreet(team.getStreet());
            }
            if (StringUtils.isNotBlank(team.getStreetName())) {
                dbTeam.setStreetName(team.getStreetName());
            }
            return teamDao.updateTeam(dbTeam);
        }
        return dbTeam;
    }

    @Override
    public void delete(String id) {
        Team dbTeam = this.getById(id);
        if (dbTeam != null) {
            teamDao.delete(id);
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

}
