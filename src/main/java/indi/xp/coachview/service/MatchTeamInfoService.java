package indi.xp.coachview.service;

import java.util.List;

import indi.xp.coachview.model.MatchTeamInfo;

public interface MatchTeamInfoService {

    public MatchTeamInfo getById(String id);

    public List<MatchTeamInfo> findByIdList(List<String> idList);

    public MatchTeamInfo add(MatchTeamInfo matchTeamInfo);

    public MatchTeamInfo update(MatchTeamInfo matchTeamInfo);

    public void delete(String id);

    public void batchDelete(List<String> idList);

    public List<MatchTeamInfo> findListByMatchId(String matchId);

    public MatchTeamInfo getByMatchId(String matchId, boolean isOpponent);

}
