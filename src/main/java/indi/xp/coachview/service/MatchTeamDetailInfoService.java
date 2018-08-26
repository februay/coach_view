package indi.xp.coachview.service;

import java.util.List;

import indi.xp.coachview.model.MatchTeamDetailInfo;

public interface MatchTeamDetailInfoService {

    public MatchTeamDetailInfo getById(String id);

    public List<MatchTeamDetailInfo> findByIdList(List<String> idList);

    public MatchTeamDetailInfo add(MatchTeamDetailInfo matchTeamDetailInfo);

    public MatchTeamDetailInfo update(MatchTeamDetailInfo matchTeamDetailInfo);

    public void delete(String id);

    public void batchDelete(List<String> idList);

    public List<MatchTeamDetailInfo> findListByMatchId(String matchId);

    public List<MatchTeamDetailInfo> findListByMatchId(String matchId, boolean isOpponent);

    public void deleteByMatchId(String matchId);

}
