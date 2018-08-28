package indi.xp.coachview.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.common.BusinessErrorCodeEnum;
import indi.xp.coachview.dao.TeamMemberDao;
import indi.xp.coachview.model.Team;
import indi.xp.coachview.model.TeamMember;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.service.TeamMemberService;
import indi.xp.common.exception.BusinessException;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.StringUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class TeamMemberServiceImpl implements TeamMemberService {
    
    private static final Logger logger = LoggerFactory.getLogger(TeamMemberServiceImpl.class); 

    @Autowired
    private TeamMemberDao teamMemberDao;

    @Override
    public TeamMember getById(String id) {
        return teamMemberDao.getTeamMemberById(id);
    }

    @Override
    public List<TeamMember> findByIdList(List<String> idList) {
        return teamMemberDao.getTeamMemberByIdList(idList);
    }

    @Override
    public List<TeamMember> findList() {
        return teamMemberDao.findTeamMemberList();
    }

    @Override
    public TeamMember add(TeamMember teamMember) {
        if (this.checkTeamMemberExists(teamMember)) {
            BusinessException be = new BusinessException(BusinessErrorCodeEnum.TEAM_MEMBER_EXISTS);
            logger.warn(BusinessErrorCodeEnum.TEAM_MEMBER_EXISTS.getMessage(), be);
            throw be;
        }
        String currentTime = DateUtils.getDateTime();
        teamMember.setMemberId(UuidUtils.generateUUID());
        teamMember.setCreateTime(currentTime);
        teamMember.setDeleteStatus(false);
        return teamMemberDao.addTeamMember(teamMember);
    }

    @Override
    public TeamMember update(TeamMember teamMember) {
        TeamMember dbTeamMember = teamMember != null ? this.getById(teamMember.getMemberId()) : null;
        if (dbTeamMember != null) {
            if (teamMember.getNumber() != null) {
                if (this.checkTeamMemberExists(teamMember)) {
                    BusinessException be = new BusinessException(BusinessErrorCodeEnum.TEAM_MEMBER_EXISTS);
                    logger.warn(BusinessErrorCodeEnum.TEAM_MEMBER_EXISTS.getMessage(), be);
                    throw be;
                }
                dbTeamMember.setNumber(teamMember.getNumber());
            }
            if (teamMember.getName() != null) {
                dbTeamMember.setName(teamMember.getName());
            }
            if (teamMember.getIdNumber() != null) {
                dbTeamMember.setIdNumber(teamMember.getIdNumber());
            }
            if (teamMember.getAge() != null) {
                dbTeamMember.setAge(teamMember.getAge());
            }
            if (teamMember.getHeight() != null) {
                dbTeamMember.setHeight(teamMember.getHeight());
            }
            if (teamMember.getWeight() != null) {
                dbTeamMember.setWeight(teamMember.getWeight());
            }
            if (teamMember.getPhoto() != null) {
                dbTeamMember.setPhoto(teamMember.getPhoto());
            }
            if (teamMember.getFirstPosition() != null) {
                dbTeamMember.setFirstPosition(teamMember.getFirstPosition());
            }
            if (teamMember.getSecondPosition() != null) {
                dbTeamMember.setSecondPosition(teamMember.getSecondPosition());
            }
            if (teamMember.getAttack() != null) {
                dbTeamMember.setAttack(teamMember.getAttack());
            }
            if (teamMember.getSpeed() != null) {
                dbTeamMember.setSpeed(teamMember.getSpeed());
            }
            if (teamMember.getTechnology() != null) {
                dbTeamMember.setTechnology(teamMember.getTechnology());
            }
            if (teamMember.getDefense() != null) {
                dbTeamMember.setDefense(teamMember.getDefense());
            }
            if (teamMember.getTeam() != null) {
                dbTeamMember.setTeam(teamMember.getTeam());
            }
            if (teamMember.getEndurance() != null) {
                dbTeamMember.setEndurance(teamMember.getEndurance());
            }
            if (teamMember.getTeamId() != null) {
                dbTeamMember.setTeamId(teamMember.getTeamId());
            }
            if (teamMember.getTeamName() != null) {
                dbTeamMember.setTeamName(teamMember.getTeamName());
            }
            return teamMemberDao.updateTeamMember(dbTeamMember);
        }
        return dbTeamMember;
    }

    @Override
    public void delete(String id) {
        TeamMember dbTeamMember = this.getById(id);
        if (dbTeamMember != null) {
            teamMemberDao.delete(id);
        }
    }

    @Override
    public List<TeamMember> findTeamMemberListByTeamId(String teamId) {
        return teamMemberDao.findListByTeamId(teamId);
    }

    @Override
    public List<ListItemVo> findTeamMemberItemList() {
        return teamMemberDao.findTeamMemberItemList();
    }

    @Override
    public void deleteByTeamId(String teamId) {
        teamMemberDao.deleteByTeamId(teamId);
    }

    @Override
    public void syncTeamMemberTeamInfo(Team team) {
        teamMemberDao.syncTeamMemberTeamInfo(team);
    }
    
    @Override
    public boolean checkTeamMemberExists(TeamMember teamMember) {
        boolean exists = false;
        if (teamMember != null && StringUtils.isNotBlank(teamMember.getNumber())) {
            String teamId = teamMember.getTeamId();
            String number = teamMember.getNumber();
            String memberId = teamMember.getMemberId();
            TeamMember dbMember = this.getByNumber(teamId, number);
            return dbMember != null
                && !dbMember.getMemberId().equals(memberId);
        }
        return exists;
    }
    
    @Override
    public TeamMember getByNumber(String teamId, String number) {
        return teamMemberDao.getByNumber(teamId, number);
    }
}
