package indi.xp.coachview.model.vo;

import java.io.Serializable;

import indi.xp.coachview.model.Match;
import indi.xp.coachview.model.MatchTeamInfo;

public class TeamSingleMatchDataVo implements Serializable {

    private static final long serialVersionUID = -4761012491524932926L;

    private Match matchInfo;
    private MatchTeamInfo teamInfo;
    private MatchTeamInfo opponentTeamInfo;

    public Match getMatchInfo() {
        return matchInfo;
    }

    public void setMatchInfo(Match matchInfo) {
        this.matchInfo = matchInfo;
    }

    public MatchTeamInfo getTeamInfo() {
        return teamInfo;
    }

    public void setTeamInfo(MatchTeamInfo teamInfo) {
        this.teamInfo = teamInfo;
    }

    public MatchTeamInfo getOpponentTeamInfo() {
        return opponentTeamInfo;
    }

    public void setOpponentTeamInfo(MatchTeamInfo opponentTeamInfo) {
        this.opponentTeamInfo = opponentTeamInfo;
    }

}
