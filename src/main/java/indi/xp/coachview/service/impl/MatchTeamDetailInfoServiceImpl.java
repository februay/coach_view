package indi.xp.coachview.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.MatchTeamDetailInfoDao;
import indi.xp.coachview.model.MatchTeamDetailInfo;
import indi.xp.coachview.service.MatchTeamDetailInfoService;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class MatchTeamDetailInfoServiceImpl implements MatchTeamDetailInfoService {

    @Autowired
    private MatchTeamDetailInfoDao matchTeamDetailInfoDao;

    @Override
    public MatchTeamDetailInfo getById(String id) {
        return matchTeamDetailInfoDao.getById(id);
    }

    @Override
    public List<MatchTeamDetailInfo> findByIdList(List<String> idList) {
        return matchTeamDetailInfoDao.findByIdList(idList);
    }

    @Override
    public MatchTeamDetailInfo add(MatchTeamDetailInfo matchTeamDetailInfo) {
        String currentTime = DateUtils.getDateTime();
        matchTeamDetailInfo.setUuid(UuidUtils.generateUUID());
        matchTeamDetailInfo.setCreateTime(currentTime);
        matchTeamDetailInfo.setDeleteStatus(false);
        return matchTeamDetailInfoDao.add(matchTeamDetailInfo);
    }

    @Override
    public MatchTeamDetailInfo update(MatchTeamDetailInfo matchTeamDetailInfo) {
        MatchTeamDetailInfo dbMatchTeamDetailInfo = matchTeamDetailInfo != null
            ? this.getById(matchTeamDetailInfo.getUuid())
            : null;
        if (dbMatchTeamDetailInfo != null) {
            // TODO:
            return matchTeamDetailInfoDao.update(dbMatchTeamDetailInfo);
        }
        return dbMatchTeamDetailInfo;
    }

    @Override
    public void delete(String id) {
        MatchTeamDetailInfo dbMatchTeamDetailInfo = this.getById(id);
        if (dbMatchTeamDetailInfo != null) {
            matchTeamDetailInfoDao.delete(id);
        }
    }

    @Override
    public List<MatchTeamDetailInfo> findListByMatchId(String matchId) {
        return matchTeamDetailInfoDao.findListByMatchId(matchId);
    }

    @Override
    public List<MatchTeamDetailInfo> findListByMatchId(String matchId, boolean isOpponent) {
        return matchTeamDetailInfoDao.findListByMatchId(matchId, isOpponent);
    }

    @Override
    public void batchDelete(List<String> idList) {
        matchTeamDetailInfoDao.batchDelete(idList);
    }

    @Override
    public void deleteByMatchId(String matchId) {
        matchTeamDetailInfoDao.deleteByMatchId(matchId);
    }

}
