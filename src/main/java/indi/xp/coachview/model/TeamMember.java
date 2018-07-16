package indi.xp.coachview.model;

import java.io.Serializable;

public class TeamMember implements Serializable {

    private static final long serialVersionUID = -4173171901498089727L;

    private String memberId;
    private String number;
    private String name;
    private String idNumber;
    private String age;
    private String height;
    private String weight;
    private String photo;
    private String firstPosition;
    private String secondPosition;
    private String teamId;
    private String teamName;
    private String createTime;
    private Boolean deleteStatus;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFirstPosition() {
        return firstPosition;
    }

    public void setFirstPosition(String firstPosition) {
        this.firstPosition = firstPosition;
    }

    public String getSecondPosition() {
        return secondPosition;
    }

    public void setSecondPosition(String secondPosition) {
        this.secondPosition = secondPosition;
    }

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @Override
    public String toString() {
        return "TeamMember [memberId=" + memberId + ", number=" + number + ", name=" + name + ", idNumber=" + idNumber
            + ", age=" + age + ", height=" + height + ", weight=" + weight + ", photo=" + photo + ", firstPosition="
            + firstPosition + ", secondPosition=" + secondPosition + ", teamId=" + teamId + ", teamName=" + teamName
            + ", createTime=" + createTime + ", deleteStatus=" + deleteStatus + "]";
    }

}
