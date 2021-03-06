package indi.xp.coachview.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.ObjectUtils;

public class MatchTeamMemberInfo implements Serializable {

    private static final long serialVersionUID = 5428518918142169492L;
    
    public static final String defaultName = "球员数据";

    public static final List<String> defaultHeaderList = new ArrayList<String>(Arrays.asList("球员编号", "射门次数（次）",
        "射正次数（次）", "传球次数（脚）", "传球成功次数（脚）", "传球成功率（%）", "跑动距离（km）", "抢断次数", "进球数", "助攻数", "位置"));

    public static final Map<String, String> nameToPropertyMapping = new HashMap<>();
    static {
        nameToPropertyMapping.put("球员编号", "memberNumber");
        nameToPropertyMapping.put("射门次数（次）", "shootCount");
        nameToPropertyMapping.put("射正次数（次）", "shootTargetCount");
        nameToPropertyMapping.put("传球次数（脚）", "passCount");
        nameToPropertyMapping.put("传球成功次数（脚）", "passSuccessCount");
        nameToPropertyMapping.put("传球成功率（%）", "passSuccessPercentage");
        nameToPropertyMapping.put("跑动距离（km）", "runningDistance");
        nameToPropertyMapping.put("抢断次数", "steals");
        nameToPropertyMapping.put("进球数", "goals");
        nameToPropertyMapping.put("助攻数", "assist");
        nameToPropertyMapping.put("位置", "position");
    }

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
    private Integer assist; // 助攻数
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

    public Integer getAssist() {
        return assist;
    }

    public void setAssist(Integer assist) {
        this.assist = assist;
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

    public static List<List<String>> parseToRowList(List<MatchTeamMemberInfo> matchTeamMemberInfoList) {
        return ObjectUtils.parseToRowList(matchTeamMemberInfoList, defaultHeaderList, nameToPropertyMapping);
    }

    public static List<MatchTeamMemberInfo> parseToObjectList(List<List<String>> rowList, String teamId,
        String matchId) {
        List<MatchTeamMemberInfo> matchTeamMemberInfoList = ObjectUtils.parseToObjectList(rowList,
            nameToPropertyMapping, MatchTeamMemberInfo.class);
        if (CollectionUtils.isNotEmpty(matchTeamMemberInfoList)) {
            for (MatchTeamMemberInfo matchTeamMemberInfo : matchTeamMemberInfoList) {
                matchTeamMemberInfo.setTeamId(teamId);
                matchTeamMemberInfo.setMatchId(matchId);
            }
        }
        return matchTeamMemberInfoList;
    }

}
