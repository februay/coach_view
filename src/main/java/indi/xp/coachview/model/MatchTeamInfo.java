package indi.xp.coachview.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.ObjectUtils;

public class MatchTeamInfo implements Serializable {

    private static final long serialVersionUID = 3336239756901640456L;
    
    public static final String defaultName = "球队数据";

    public static final List<String> defaultHeaderList = new ArrayList<String>(Arrays.asList("场次", "射门次数（次）", "射正次数（次）",
        "传球次数（脚）", "传球成功次数（脚）", "传球成功率（%）", "控球率（%）", "跑动距离（km）", "抢断次数", "进球数", "失球数", "胜平负", "是否对手球队"));

    public static final Map<String, String> nameToPropertyMapping = new HashMap<>();
    static {
        nameToPropertyMapping.put("场次", "matchSession");
        nameToPropertyMapping.put("射门次数（次）", "shootCount");
        nameToPropertyMapping.put("射正次数（次）", "shootTargetCount");
        nameToPropertyMapping.put("传球次数（脚）", "passCount");
        nameToPropertyMapping.put("传球成功次数（脚）", "passSuccessCount");
        nameToPropertyMapping.put("传球成功率（%）", "passSuccessPercentage");
        nameToPropertyMapping.put("控球率（%）", "possessionPercentage");
        nameToPropertyMapping.put("跑动距离（km）", "runningDistance");
        nameToPropertyMapping.put("抢断次数", "steals");
        nameToPropertyMapping.put("进球数", "goals");
        nameToPropertyMapping.put("失球数", "losses");
        nameToPropertyMapping.put("胜平负", "matchResult");
        nameToPropertyMapping.put("是否对手球队", "opponent");
    }

    private String uuid;
    private String matchId;
    private String teamId;
    private String matchSession; // 比赛场次
    private Integer shootCount; // 射门次数
    private Integer shootTargetCount; // 射正次数
    private Integer passCount; // 传球次数
    private Integer passSuccessCount; // 传球成功次数
    private Double passSuccessPercentage; // 传球成功率
    private Double possessionPercentage; // 控球率
    private Double runningDistance; // 跑动距离(km)
    private Integer steals; // 抢断次数
    private Integer goals; // 进球数
    private Integer losses; // 丢球数
    private String matchResult; // 比赛结果: 胜|平|负
    private Boolean opponent; // 是否对手球队， 0 : 本队数据， 1： 对手球队数据
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

    public String getMatchSession() {
        return matchSession;
    }

    public void setMatchSession(String matchSession) {
        this.matchSession = matchSession;
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

    public Double getPossessionPercentage() {
        return possessionPercentage;
    }

    public void setPossessionPercentage(Double possessionPercentage) {
        this.possessionPercentage = possessionPercentage;
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
    
    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public String getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }

    public Boolean getOpponent() {
        return opponent;
    }

    public void setOpponent(Boolean opponent) {
        this.opponent = opponent;
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

    public static List<List<String>> parseToRowList(List<MatchTeamInfo> matchTeamInfoList) {
        return ObjectUtils.parseToRowList(matchTeamInfoList, defaultHeaderList, nameToPropertyMapping);
    }

    public static List<MatchTeamInfo> parseToObjectList(List<List<String>> rowList, String teamId, String matchId) {
        List<MatchTeamInfo> matchTeamInfoList = ObjectUtils.parseToObjectList(rowList, nameToPropertyMapping,
            MatchTeamInfo.class);
        if (CollectionUtils.isNotEmpty(matchTeamInfoList)) {
            for (MatchTeamInfo matchTeamInfo : matchTeamInfoList) {
                matchTeamInfo.setTeamId(teamId);
                matchTeamInfo.setMatchId(matchId);
            }
        }
        return matchTeamInfoList;
    }
}
