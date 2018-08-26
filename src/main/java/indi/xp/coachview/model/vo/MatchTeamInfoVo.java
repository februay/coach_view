package indi.xp.coachview.model.vo;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.BeanUtils;

import indi.xp.coachview.model.MatchTeamDetailInfo;
import indi.xp.coachview.model.MatchTeamInfo;

public class MatchTeamInfoVo extends MatchTeamInfo implements Serializable {

    private static final long serialVersionUID = 3616163520947713719L;

    private List<MatchTeamDetailInfo> details;

    public MatchTeamInfoVo() {
    }

    public MatchTeamInfoVo(MatchTeamInfo matchTeamInfo) {
        if (matchTeamInfo != null) {
            BeanUtils.copyProperties(matchTeamInfo, this);
        }
    }

    public List<MatchTeamDetailInfo> getDetails() {
        return details;
    }

    public void setDetails(List<MatchTeamDetailInfo> details) {
        this.details = details;
    }

}