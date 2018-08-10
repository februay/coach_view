package indi.xp.coachview.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.MatchDao;
import indi.xp.coachview.model.Match;
import indi.xp.coachview.service.MatchService;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchDao matchDao;

    @Override
    public Match getById(String id) {
        return matchDao.getById(id);
    }

    @Override
    public List<Match> findByIdList(List<String> idList) {
        return matchDao.findByIdList(idList);
    }

    @Override
    public Match add(Match match) {
        String currentTime = DateUtils.getDateTime();
        match.setMatchId(UuidUtils.generateUUID());
        match.setCreateTime(currentTime);
        match.setDeleteStatus(false);
        return matchDao.add(match);
    }

    @Override
    public Match update(Match match) {
        Match dbMatch = match != null ? this.getById(match.getMatchId()) : null;
        if (dbMatch != null) {
            // TODO:
            return matchDao.update(dbMatch);
        }
        return dbMatch;
    }

    @Override
    public void delete(String id) {
        Match dbMatch = this.getById(id);
        if (dbMatch != null) {
            matchDao.delete(id);
        }
    }

    @Override
    public List<Match> findListByTeamId(String teamId) {
        return matchDao.findListByTeamId(teamId);
    }

    @Override
    public void batchDelete(List<String> idList) {
        matchDao.batchDelete(idList);
    }

}
