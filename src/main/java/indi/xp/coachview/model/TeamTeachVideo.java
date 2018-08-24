package indi.xp.coachview.model;

import java.io.Serializable;

public class TeamTeachVideo implements Serializable {

    private static final long serialVersionUID = -6857018985535156888L;

    private String videoId;
    private String teamId;
    private String name; // 视频名称
    private String description; // 视频描述
    private String video; // 视频地址
    private String videoCoverImg; // 视频封面图片
    private String creatorId;
    private String createTime;
    private Boolean deleteStatus; // 是否删除

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVideoCoverImg() {
        return videoCoverImg;
    }

    public void setVideoCoverImg(String videoCoverImg) {
        this.videoCoverImg = videoCoverImg;
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

}
