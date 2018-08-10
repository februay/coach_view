package indi.xp.coachview.model;

import java.io.Serializable;

public class MatchTeamMemberInfo implements Serializable {

    private static final long serialVersionUID = 5428518918142169492L;

    private String uuid;
    private String matchId;
    private String teamId;
    private String memberId;
    private String memberNumber;
    private Integer shootCount; // 射门次数
    private Integer shootTargetCount; // 射正次数
    private Integer passCount; // 传球次数
    private Integer passSuccessCount; // 传球成功次数
    private Double passSuccessPercentage; // 传球成功率
    private Double runningDistance; // 跑动距离(km)
    private Integer steals; // 抢断次数
    private Integer goals; // 进球数
    private String position; // 位置
    private String createTime;
    private Boolean deleteStatus; // 是否删除

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public Integer getShootCount() {
        return shootCount;
    }

    public void setShootCount(Integer shootCount) {
        this.shootCount = shootCount;
    }

    public Integer getShootTargetCount() {
        return shootTargetCount;
    }

    public void setShootTargetCount(Integer shootTargetCount) {
        this.shootTargetCount = shootTargetCount;
    }

    public Integer getPassCount() {
        return passCount;
    }

    public void setPassCount(Integer passCount) {
        this.passCount = passCount;
    }

    public Integer getPassSuccessCount() {
        return passSuccessCount;
    }

    public void setPassSuccessCount(Integer passSuccessCount) {
        this.passSuccessCount = passSuccessCount;
    }

    public Double getPassSuccessPercentage() {
        return passSuccessPercentage;
    }

    public void setPassSuccessPercentage(Double passSuccessPercentage) {
        this.passSuccessPercentage = passSuccessPercentage;
    }

    public Double getRunningDistance() {
        return runningDistance;
    }

    public void setRunningDistance(Double runningDistance) {
        this.runningDistance = runningDistance;
    }

    public Integer getSteals() {
        return steals;
    }

    public void setSteals(Integer steals) {
        this.steals = steals;
    }

    public Integer getGoals() {
        return goals;
    }

    public void setGoals(Integer goals) {
        this.goals = goals;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

}
