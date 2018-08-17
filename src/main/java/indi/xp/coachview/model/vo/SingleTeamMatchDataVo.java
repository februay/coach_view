package indi.xp.coachview.model.vo;

import java.io.Serializable;
import java.util.Map;

public class SingleTeamMatchDataVo implements Serializable {

    private static final long serialVersionUID = -2535528467426623203L;

    private Map<String, Object> teamInfo;
    private Map<String, Object> opponentTeamInfo;

    public Map<String, Object> getTeamInfo() {
        return teamInfo;
    }

    public void setTeamInfo(Map<String, Object> teamInfo) {
        this.teamInfo = teamInfo;
    }

    public Map<String, Object> getOpponentTeamInfo() {
        return opponentTeamInfo;
    }

    public void setOpponentTeamInfo(Map<String, Object> opponentTeamInfo) {
        this.opponentTeamInfo = opponentTeamInfo;
    }

}
