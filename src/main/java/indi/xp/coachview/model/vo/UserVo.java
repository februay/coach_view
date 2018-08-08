package indi.xp.coachview.model.vo;

import java.io.Serializable;
import java.util.List;

public class UserVo implements Serializable {

    private static final long serialVersionUID = 7318789932910212804L;

    private String uid;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String name;
    private String phone; // 手机号
    private String company; // 公司
    private String department; // 部门
    private String title; // 职位
    private String activeTime;
    private String creatorId;
    private String createTime;
    private String clubId;
    private String schoolId;
    private String teamId;

    private List<String> roles;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "UserVo [uid=" + uid + ", userName=" + userName + ", userEmail=" + userEmail + ", userPassword="
            + userPassword + ", name=" + name + ", phone=" + phone + ", company=" + company + ", department="
            + department + ", title=" + title + ", activeTime=" + activeTime + ", createTime=" + createTime
            + ", clubId=" + clubId + ", schoolId=" + schoolId + ", teamId=" + teamId + ", roles=" + roles + "]";
    }

}
