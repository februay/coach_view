package indi.xp.coachview.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.ObjectUtils;

public class TeamMember implements Serializable {

    private static final long serialVersionUID = -4173171901498089727L;

    public static final String defaultName = "球员信息";

    public static final List<String> defaultHeaderList = new ArrayList<String>(
        Arrays.asList("球衣号", "姓名", "身份证号", "年龄", "身高", "体重", "第一位置", "第二位置", "进攻", "速度", "技术", "防守", "团队", "耐力", "照片"));

    public static final Map<String, String> nameToPropertyMapping = new HashMap<>();
    static {
        nameToPropertyMapping.put("球衣号", "number");
        nameToPropertyMapping.put("姓名", "name");
        nameToPropertyMapping.put("身份证号", "idNumber");
        nameToPropertyMapping.put("年龄", "age");
        nameToPropertyMapping.put("身高", "height");
        nameToPropertyMapping.put("体重", "weight");
        nameToPropertyMapping.put("第一位置", "firstPosition");
        nameToPropertyMapping.put("第二位置", "secondPosition");
        nameToPropertyMapping.put("进攻", "attack");
        nameToPropertyMapping.put("速度", "speed");
        nameToPropertyMapping.put("技术", "technology");
        nameToPropertyMapping.put("防守", "defense");
        nameToPropertyMapping.put("团队", "team");
        nameToPropertyMapping.put("耐力", "endurance");
        nameToPropertyMapping.put("照片", "photo");
    }

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
    private Double attack; // 进攻评分
    private Double speed; // 速度评分
    private Double technology; // 技术评分
    private Double defense; // 防守评分
    private Double team; // 团队评分
    private Double endurance; // 耐力评分
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

    public Double getAttack() {
        return attack;
    }

    public void setAttack(Double attack) {
        this.attack = attack;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getTechnology() {
        return technology;
    }

    public void setTechnology(Double technology) {
        this.technology = technology;
    }

    public Double getDefense() {
        return defense;
    }

    public void setDefense(Double defense) {
        this.defense = defense;
    }

    public Double getTeam() {
        return team;
    }

    public void setTeam(Double team) {
        this.team = team;
    }

    public Double getEndurance() {
        return endurance;
    }

    public void setEndurance(Double endurance) {
        this.endurance = endurance;
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
            + firstPosition + ", secondPosition=" + secondPosition + ", attack=" + attack + ", speed=" + speed
            + ", technology=" + technology + ", defense=" + defense + ", team=" + team + ", endurance=" + endurance
            + ", teamId=" + teamId + ", teamName=" + teamName + ", createTime=" + createTime + ", deleteStatus="
            + deleteStatus + "]";
    }

    public static List<List<String>> parseToRowList(List<TeamMember> memberList) {
        return ObjectUtils.parseToRowList(memberList, defaultHeaderList, nameToPropertyMapping);
    }

    public static List<TeamMember> parseToObjectList(List<List<String>> rowList, String teamId, String teamName) {
        List<TeamMember> memberList = ObjectUtils.parseToObjectList(rowList, nameToPropertyMapping, TeamMember.class);
        if (CollectionUtils.isNotEmpty(memberList)) {
            for (TeamMember member : memberList) {
                member.setTeamId(teamId);
                member.setTeamName(teamName);
            }
        }
        return memberList;
    }

}
