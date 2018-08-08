package indi.xp.coachview.model.vo;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.BeanUtils;

import indi.xp.coachview.model.Team;
import indi.xp.coachview.model.TeamCoach;
import indi.xp.coachview.model.TeamMember;
import indi.xp.coachview.session.SessionConext;

public class TeamVo extends Team implements Serializable {

    private static final long serialVersionUID = 8071951797644276328L;

    private Boolean isAdmin; // 是否是管理员

    private List<TeamMember> teamMemberList;
    private List<TeamCoach> teamCoachList;

    public TeamVo() {
    }

    public TeamVo(Team team) {
        if (team != null) {
            BeanUtils.copyProperties(team, this);
            SessionConext sessionContext = SessionConext.getThreadLocalSessionContext();
            boolean isAdmin = sessionContext != null && sessionContext.getSessionUser() != null
                && sessionContext.getSessionUser().getUid().equals(this.getAdminId());
            this.setIsAdmin(isAdmin);
        }
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public List<TeamMember> getTeamMemberList() {
        return teamMemberList;
    }

    public void setTeamMemberList(List<TeamMember> teamMemberList) {
        this.teamMemberList = teamMemberList;
    }

    public List<TeamCoach> getTeamCoachList() {
        return teamCoachList;
    }

    public void setTeamCoachList(List<TeamCoach> teamCoachList) {
        this.teamCoachList = teamCoachList;
    }

}
