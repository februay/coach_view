package indi.xp.coachview.model.vo;

import java.io.Serializable;

public class UserSignUpVo implements Serializable {

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

    @Override
    public String toString() {
        return "UserSignVo [uid=" + uid + ", userName=" + userName + ", userEmail=" + userEmail + ", userPassword="
            + userPassword + ", name=" + name + ", phone=" + phone + ", company=" + company + ", department="
            + department + ", title=" + title + "]";
    }

}
