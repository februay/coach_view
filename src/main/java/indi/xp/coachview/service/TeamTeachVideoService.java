package indi.xp.coachview.service;

import java.util.List;

import indi.xp.coachview.model.TeamTeachVideo;

public interface TeamTeachVideoService {

    public TeamTeachVideo getById(String id);

    public TeamTeachVideo add(TeamTeachVideo teachVideo);

    public TeamTeachVideo update(TeamTeachVideo teachVideo);

    public void delete(String id);

    public void batchDelete(List<String> idList);

    public List<TeamTeachVideo> findListByTeamId(String teamId);
}
