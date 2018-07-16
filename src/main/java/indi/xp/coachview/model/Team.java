package indi.xp.coachview.model;

import java.io.Serializable;

public class Team implements Serializable {

    private static final long serialVersionUID = -3626039532365754781L;

    private String teamId;
    private String teamName;
    private String schoolId;
    private String schoolName;
    private String adminId;
    private String adminName;
    private String status;
    private String createTime;
    private String activeTime;
    private Boolean deleteStatus; // 是否删除

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @Override
    public String toString() {
        return "Team [teamId=" + teamId + ", teamName=" + teamName + ", schoolId=" + schoolId + ", schoolName="
            + schoolName + ", adminId=" + adminId + ", adminName=" + adminName + ", status=" + status + ", createTime="
            + createTime + ", activeTime=" + activeTime + ", deleteStatus=" + deleteStatus + "]";
    }

}
