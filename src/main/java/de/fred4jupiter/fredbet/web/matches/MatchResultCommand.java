package de.fred4jupiter.fredbet.web.matches;

import de.fred4jupiter.fredbet.web.bet.RedirectViewName;
import de.fred4jupiter.fredbet.web.validation.TeamResultConstraint;

@TeamResultConstraint(message = "{msg.invalidTeamResult}")
public class MatchResultCommand {

    private String matchId;

    private String teamNameOne;
    private String teamNameTwo;

    private String iconPathTeamOne;
    private String iconPathTeamTwo;

    private Integer teamResultOne;

    private Integer teamResultTwo;

    private boolean penaltyWinnerOne;

    private boolean groupMatch;

    private String redirectViewName;

    public String getBackUrl() {
        String view = RedirectViewName.resolveBackUrl(redirectViewName);
        return view + "#" + getMatchId();
    }

    public String getRedirectViewName() {
        return redirectViewName;
    }

    public void setRedirectViewName(String redirectViewName) {
        this.redirectViewName = redirectViewName;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public Integer getTeamResultOne() {
        return teamResultOne;
    }

    public void setTeamResultOne(Integer teamResultOne) {
        this.teamResultOne = teamResultOne;
    }

    public Integer getTeamResultTwo() {
        return teamResultTwo;
    }

    public void setTeamResultTwo(Integer teamResultTwo) {
        this.teamResultTwo = teamResultTwo;
    }

    public boolean isPenaltyWinnerOne() {
        return penaltyWinnerOne;
    }

    public void setPenaltyWinnerOne(boolean penaltyWinnerOne) {
        this.penaltyWinnerOne = penaltyWinnerOne;
    }

    public boolean isGroupMatch() {
        return this.groupMatch;
    }

    public void setGroupMatch(boolean groupMatch) {
        this.groupMatch = groupMatch;
    }

    public String getTeamNameOne() {
        return teamNameOne;
    }

    public void setTeamNameOne(String teamNameOne) {
        this.teamNameOne = teamNameOne;
    }

    public String getTeamNameTwo() {
        return teamNameTwo;
    }

    public void setTeamNameTwo(String teamNameTwo) {
        this.teamNameTwo = teamNameTwo;
    }

    public void setIconPathTeamOne(String iconPathTeamOne) {
        this.iconPathTeamOne = iconPathTeamOne;
    }

    public void setIconPathTeamTwo(String iconPathTeamTwo) {
        this.iconPathTeamTwo = iconPathTeamTwo;
    }

    public String getIconPathTeamOne() {
        return iconPathTeamOne;
    }

    public String getIconPathTeamTwo() {
        return iconPathTeamTwo;
    }
}
