package indi.xp.coachview.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.MatchTeamMemberInfoDao;
import indi.xp.coachview.model.MatchTeamMemberInfo;
import indi.xp.coachview.service.MatchTeamMemberInfoService;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.UuidUtils;

@Service
public class MatchTeamMemberInfoServiceImpl implements MatchTeamMemberInfoService {

    @Autowired
    private MatchTeamMemberInfoDao matchTeamMemberInfoDao;

    @Override
    public MatchTeamMemberInfo getById(String id) {
        return matchTeamMemberInfoDao.getById(id);
    }

    @Override
    public List<MatchTeamMemberInfo> findByIdList(List<String> idList) {
        return matchTeamMemberInfoDao.findByIdList(idList);
    }

    @Override
    public MatchTeamMemberInfo add(MatchTeamMemberInfo matchTeamMemberInfo) {
        String currentTime = DateUtils.getDateTime();
        matchTeamMemberInfo.setUuid(UuidUtils.generateUUID());
        matchTeamMemberInfo.setCreateTime(currentTime);
        matchTeamMemberInfo.setDeleteStatus(false);
        return matchTeamMemberInfoDao.add(matchTeamMemberInfo);
    }

    @Override
    public MatchTeamMemberInfo update(MatchTeamMemberInfo matchTeamMemberInfo) {
        MatchTeamMemberInfo dbMatchTeamMemberInfo = matchTeamMemberInfo != null
            ? this.getById(matchTeamMemberInfo.getUuid())
            : null;
        if (dbMatchTeamMemberInfo != null) {
            // TODO:
            return matchTeamMemberInfoDao.update(dbMatchTeamMemberInfo);
        }
        return dbMatchTeamMemberInfo;
    }

    @Override
    public void delete(String id) {
        MatchTeamMemberInfo dbMatchTeamMemberInfo = this.getById(id);
        if (dbMatchTeamMemberInfo != null) {
            matchTeamMemberInfoDao.delete(id);
        }
    }

    @Override
    public List<MatchTeamMemberInfo> findListByMatchId(String matchId) {
        return matchTeamMemberInfoDao.findListByMatchId(matchId);
    }

    @Override
    public void batchDelete(List<String> idList) {
        matchTeamMemberInfoDao.batchDelete(idList);
    }

    @Override
    public void deleteByMatchId(String matchId) {
        matchTeamMemberInfoDao.deleteByMatchId(matchId);
    }

}
