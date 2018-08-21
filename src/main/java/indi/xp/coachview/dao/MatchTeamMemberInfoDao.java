package indi.xp.coachview.dao;

import java.util.List;

import indi.xp.coachview.model.MatchTeamMemberInfo;

public interface MatchTeamMemberInfoDao {

    public MatchTeamMemberInfo getById(String id);

    public List<MatchTeamMemberInfo> findByIdList(List<String> idList);

    public MatchTeamMemberInfo add(MatchTeamMemberInfo matchTeamMemberInfo);

    public MatchTeamMemberInfo update(MatchTeamMemberInfo matchTeamMemberInfo);

    public void delete(String id);

    public void batchDelete(List<String> idList);

    public List<MatchTeamMemberInfo> findListByMatchId(String matchId);

    public void deleteByMatchId(String matchId);

}
