package indi.xp.coachview.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.TeamTeachVideoDao;
import indi.xp.coachview.model.TeamTeachVideo;
import indi.xp.coachview.service.TeamTeachVideoService;
import indi.xp.coachview.session.SessionConext;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class TeamTeachVideoServiceImpl implements TeamTeachVideoService {

    @Autowired
    private TeamTeachVideoDao teamTeachVideoDao;

    @Override
    public TeamTeachVideo getById(String id) {
        return teamTeachVideoDao.getById(id);
    }

    @Override
    public TeamTeachVideo add(TeamTeachVideo teamTeachVideo) {
        String currentTime = DateUtils.getDateTime();
        teamTeachVideo.setVideoId(UuidUtils.generateUUID());
        SessionConext sessionContext = SessionConext.getThreadLocalSessionContext();
        if (sessionContext != null) {
            teamTeachVideo.setCreatorId(sessionContext.getUid());
        }
        teamTeachVideo.setCreateTime(currentTime);
        teamTeachVideo.setDeleteStatus(false);
        return teamTeachVideoDao.add(teamTeachVideo);
    }

    @Override
    public TeamTeachVideo update(TeamTeachVideo teamTeachVideo) {
        TeamTeachVideo dbTeamTeachVideo = teamTeachVideo != null ? this.getById(teamTeachVideo.getVideoId()) : null;
        if (dbTeamTeachVideo != null) {
            if (teamTeachVideo.getName() != null) {
                dbTeamTeachVideo.setName(teamTeachVideo.getName());
            }
            if (teamTeachVideo.getDescription() != null) {
                dbTeamTeachVideo.setDescription(teamTeachVideo.getDescription());
            }
            if (teamTeachVideo.getVideo() != null) {
                dbTeamTeachVideo.setVideo(teamTeachVideo.getVideo());
            }
            if (teamTeachVideo.getVideoCoverImg() != null) {
                dbTeamTeachVideo.setVideoCoverImg(teamTeachVideo.getVideoCoverImg());
            }
            return teamTeachVideoDao.update(dbTeamTeachVideo);
        }
        return dbTeamTeachVideo;
    }

    @Override
    public void delete(String id) {
        TeamTeachVideo dbTeamTeachVideo = this.getById(id);
        if (dbTeamTeachVideo != null) {
            teamTeachVideoDao.delete(id);
        }
    }

    @Override
    public void batchDelete(List<String> idList) {
        teamTeachVideoDao.batchDelete(idList);
    }

    @Override
    public List<TeamTeachVideo> findListByTeamId(String teamId) {
        return teamTeachVideoDao.findListByTeamId(teamId);
    }

}
