package indi.xp.coachview.model.vo;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.BeanUtils;

import indi.xp.coachview.model.Club;
import indi.xp.coachview.session.SessionConext;

public class ClubVo extends Club implements Serializable {

    private static final long serialVersionUID = -8957273283264179669L;

    private Boolean isAdmin; // 是否是管理员

    private List<SchoolVo> schoolList;

    public ClubVo() {
    }

    public ClubVo(Club club) {
        if (club != null) {
            BeanUtils.copyProperties(club, this);
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

    public List<SchoolVo> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List<SchoolVo> schoolList) {
        this.schoolList = schoolList;
    }

}
