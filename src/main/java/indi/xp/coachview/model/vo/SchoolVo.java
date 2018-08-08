package indi.xp.coachview.model.vo;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.BeanUtils;

import indi.xp.coachview.model.School;
import indi.xp.coachview.session.SessionConext;

public class SchoolVo extends School implements Serializable {

    private static final long serialVersionUID = 8811466200260013632L;

    private Boolean isAdmin; // 是否是管理员

    private List<TeamVo> teamList;

    public SchoolVo() {
    }

    public SchoolVo(School school) {
        if (school != null) {
            BeanUtils.copyProperties(school, this);
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

    public List<TeamVo> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<TeamVo> teamList) {
        this.teamList = teamList;
    }

}
