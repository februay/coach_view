package indi.xp.coachview.model;

import java.io.Serializable;

public class School implements Serializable {

    private static final long serialVersionUID = 6980231063128526163L;
    private String schoolId;
    private String schoolName;
    private String clubId;
    private String clubName;
    private String adminId;
    private String adminName;
    private String province;
    private String city;
    private String region;
    private String county;
    private String status;
    private String createTime;
    private String activeTime;
    private Boolean deleteStatus; // 是否删除

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

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
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
        return "School [schoolId=" + schoolId + ", schoolName=" + schoolName + ", clubId=" + clubId + ", clubName="
            + clubName + ", adminId=" + adminId + ", adminName=" + adminName + ", province=" + province + ", city="
            + city + ", region=" + region + ", county=" + county + ", status=" + status + ", createTime=" + createTime
            + ", activeTime=" + activeTime + ", deleteStatus=" + deleteStatus + "]";
    }

}
