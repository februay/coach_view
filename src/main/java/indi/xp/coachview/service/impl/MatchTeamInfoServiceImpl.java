package indi.xp.coachview.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.MatchTeamInfoDao;
import indi.xp.coachview.model.MatchTeamInfo;
import indi.xp.coachview.service.MatchTeamInfoService;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class MatchTeamInfoServiceImpl implements MatchTeamInfoService {

    @Autowired
    private MatchTeamInfoDao matchTeamInfoDao;

    @Override
    public MatchTeamInfo getById(String id) {
        return matchTeamInfoDao.getById(id);
    }

    @Override
    public List<MatchTeamInfo> findByIdList(List<String> idList) {
        return matchTeamInfoDao.findByIdList(idList);
    }

    @Override
    public List<MatchTeamInfo> findByIdList(List<String> idList, boolean isOpponent) {
        return matchTeamInfoDao.findByIdList(idList, isOpponent);
    }

    @Override
    public MatchTeamInfo add(MatchTeamInfo matchTeamInfo) {
        String currentTime = DateUtils.getDateTime();
        matchTeamInfo.setUuid(UuidUtils.generateUUID());
        matchTeamInfo.setCreateTime(currentTime);
        matchTeamInfo.setDeleteStatus(false);
        return matchTeamInfoDao.add(matchTeamInfo);
    }

    @Override
    public MatchTeamInfo update(MatchTeamInfo matchTeamInfo) {
        MatchTeamInfo dbMatchTeamInfo = matchTeamInfo != null ? this.getById(matchTeamInfo.getUuid()) : null;
        if (dbMatchTeamInfo != null) {
            // TODO:
            return matchTeamInfoDao.update(dbMatchTeamInfo);
        }
        return dbMatchTeamInfo;
    }

    @Override
    public void delete(String id) {
        MatchTeamInfo dbMatchTeamInfo = this.getById(id);
        if (dbMatchTeamInfo != null) {
            matchTeamInfoDao.delete(id);
        }
    }

    @Override
    public List<MatchTeamInfo> findListByMatchId(String matchId) {
        return matchTeamInfoDao.findListByMatchId(matchId);
    }

    @Override
    public MatchTeamInfo getByMatchId(String matchId, boolean isOpponent) {
        return matchTeamInfoDao.getByMatchId(matchId, isOpponent);
    }

    @Override
    public void batchDelete(List<String> idList) {
        matchTeamInfoDao.batchDelete(idList);
    }

}
