package indi.xp.coachview.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.ObjectUtils;

public class Match implements Serializable {

    private static final long serialVersionUID = -3758063655019034907L;

    public static final String defaultName = "比赛数据";
    
    public static final List<String> defaultHeaderList = new ArrayList<String>(
        Arrays.asList("比赛编号", "比赛名称", "比赛场次", "比赛时间", "比赛视频"));

    public static final Map<String, String> nameToPropertyMapping = new HashMap<>();
    static {
        nameToPropertyMapping.put("比赛编号", "matchNumber");
        nameToPropertyMapping.put("比赛名称", "matchName");
        nameToPropertyMapping.put("比赛场次", "matchSession");
        nameToPropertyMapping.put("比赛时间", "matchTime");
        nameToPropertyMapping.put("比赛视频", "matchVideo");
    }

    private String matchId;
    private String matchNumber; // 比赛编号
    private String matchName; // 比赛名称
    private String teamId;
    private String matchSession; // 比赛场次
    private String matchTime; // 比赛时间
    private String matchVideo; // 比赛视频
    private String creatorId;
    private String createTime;
    private Boolean deleteStatus; // 是否删除

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(String matchNumber) {
        this.matchNumber = matchNumber;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
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

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getMatchVideo() {
        return matchVideo;
    }

    public void setMatchVideo(String matchVideo) {
        this.matchVideo = matchVideo;
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

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @Override
    public String toString() {
        return "Match [matchId=" + matchId + ", matchName=" + matchName + ", teamId=" + teamId + ", matchSession="
            + matchSession + ", matchTime=" + matchTime + ", matchVideo=" + matchVideo + ", creatorId=" + creatorId
            + ", createTime=" + createTime + ", deleteStatus=" + deleteStatus + "]";
    }

    public static List<List<String>> parseToRowList(List<Match> matchList) {
        return ObjectUtils.parseToRowList(matchList, defaultHeaderList, nameToPropertyMapping);
    }

    public static List<Match> parseToObjectList(List<List<String>> rowList, String teamId) {
        List<Match> matchList = ObjectUtils.parseToObjectList(rowList, nameToPropertyMapping, Match.class);
        if (CollectionUtils.isNotEmpty(matchList)) {
            for (Match match : matchList) {
                match.setTeamId(teamId);
            }
        }
        return matchList;
    }

}
