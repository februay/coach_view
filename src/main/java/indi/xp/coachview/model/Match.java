package indi.xp.coachview.model;

import java.io.Serializable;

public class Match implements Serializable {

    private static final long serialVersionUID = -3758063655019034907L;

    private String matchId;
    private String matchName;
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

}
