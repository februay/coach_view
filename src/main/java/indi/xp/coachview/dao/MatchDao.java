package indi.xp.coachview.dao;

import java.util.List;

import indi.xp.coachview.model.Match;

public interface MatchDao {

    public Match getById(String id);

    public List<Match> findByIdList(List<String> idList);

    public Match add(Match match);

    public Match update(Match match);

    public void delete(String id);

    public void batchDelete(List<String> idList);

    public List<Match> findListByTeamId(String teamId);

}
