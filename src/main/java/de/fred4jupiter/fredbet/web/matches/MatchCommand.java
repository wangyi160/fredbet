package de.fred4jupiter.fredbet.web.matches;

import de.fred4jupiter.fredbet.props.FredbetConstants;
import de.fred4jupiter.fredbet.web.AbstractMatchHeaderCommand;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MatchCommand extends AbstractMatchHeaderCommand {

    private static final String LABEL_INFO = "label-info";

    private static final String LABEL_SUCCESS = "label-success";

    private static final String LABEL_DEFAULT = "label-default";

    private static final String LABEL_INFO_PENALTY = LABEL_INFO + " " + FredbetConstants.BADGE_PENALTY_WINNER_MATCH_CSS_CLASS;

    private Long matchId;

    private Integer teamResultOne;

    private Integer teamResultTwo;

//    private Integer userBetGoalsTeamOne;
//
//    private Integer userBetGoalsTeamTwo;

//    private Integer points;
//
//    private boolean joker;

//    private boolean penaltyWinnerOneBet;
//
//    private boolean penaltyWinnerOneMatch;
    
    private double winOdds;
    private double drawOdds;
    private double loseOdds;
    

    public boolean isBettable() {
        if (hasMatchStarted() || hasMatchFinished()) {
            return false;
        }

        return true;
    }

    public boolean hasMatchFinished() {
        return teamResultOne != null && teamResultTwo != null;
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

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        builder.appendSuper(super.toString());
        builder.append("matchId", matchId);
        builder.append("teamResultOne", teamResultOne);
        builder.append("teamResultTwo", teamResultTwo);
        builder.append("group", group);
        builder.append("kickOffDate", getKickOffDate());
        return builder.toString();
    }

//    public Integer getUserBetGoalsTeamOne() {
//        return userBetGoalsTeamOne;
//    }
//
//    public void setUserBetGoalsTeamOne(Integer userBetgoalsTeamOne) {
//        this.userBetGoalsTeamOne = userBetgoalsTeamOne;
//    }
//
//    public Integer getUserBetGoalsTeamTwo() {
//        return userBetGoalsTeamTwo;
//    }
//
//    public void setUserBetGoalsTeamTwo(Integer userBetGoalsTeamTwo) {
//        this.userBetGoalsTeamTwo = userBetGoalsTeamTwo;
//    }
//
//    public Integer getPoints() {
//        if (hasMatchFinished() && points == null) {
//            return 0;
//        }
//        return points;
//    }
//
//    public void setPoints(Integer points) {
//        this.points = points;
//    }

    public boolean isGroupMatch() {
//        return this.group.name().startsWith("GROUP");
    	return true;
    }

//    public boolean isPenaltyWinnerOneBet() {
//        return penaltyWinnerOneBet;
//    }
//
//    public void setPenaltyWinnerOneBet(boolean penaltyWinnerOneBet) {
//        this.penaltyWinnerOneBet = penaltyWinnerOneBet;
//    }
//
//    public boolean isPenaltyWinnerOneMatch() {
//        return penaltyWinnerOneMatch;
//    }
//
//    public void setPenaltyWinnerOneMatch(boolean penaltyWinnerOneMatch) {
//        this.penaltyWinnerOneMatch = penaltyWinnerOneMatch;
//    }
//
//    public boolean isUndecidedBetting() {
//        return getGoalDifferenceBetting() == 0;
//    }
//
//    private Integer getGoalDifferenceBetting() {
//        if (userBetGoalsTeamOne == null || userBetGoalsTeamTwo == null) {
//            throw new IllegalStateException("No goal bets set!");
//        }
//        return Math.abs(userBetGoalsTeamOne - userBetGoalsTeamTwo);
//    }
//
    private boolean isUndecidedResult() {
        return getGoalDifferenceMatch() == 0;
    }

    private Integer getGoalDifferenceMatch() {
        if (teamResultOne == null || teamResultTwo == null) {
            throw new IllegalStateException("No goals match set!");
        }
        return Math.abs(teamResultOne - teamResultTwo);
    }

//    public String getUserBetGoalsTeamOneCssClasses() {
//        if (this.getUserBetGoalsTeamOne() == null) {
//            return LABEL_DEFAULT;
//        }
//
//        String cssClasses = LABEL_SUCCESS;
//        if (!this.isGroupMatch() && this.isUndecidedBetting() && this.isPenaltyWinnerOneBet()) {
//            cssClasses = cssClasses + " " + FredbetConstants.BADGE_PENALTY_WINNER_BET_CSS_CLASS;
//        }
//
//        if (isJoker()) {
//            cssClasses = cssClasses + " " + FredbetConstants.JOKER_CSS_CLASS;
//        }
//
//        return cssClasses;
//    }
//
//    public String getUserBetGoalsTeamTwoCssClasses() {
//        if (this.getUserBetGoalsTeamTwo() == null) {
//            return LABEL_DEFAULT;
//        }
//
//        String cssClasses = LABEL_SUCCESS;
//        if (!this.isGroupMatch() && this.isUndecidedBetting() && !this.isPenaltyWinnerOneBet()) {
//            cssClasses = cssClasses + " " + FredbetConstants.BADGE_PENALTY_WINNER_BET_CSS_CLASS;
//        }
//
//        if (isJoker()) {
//            cssClasses = cssClasses + " " + FredbetConstants.JOKER_CSS_CLASS;
//        }
//
//        return cssClasses;
//    }

    public String getTeamResultOneCssClasses() {
        if (this.getTeamResultOne() == null) {
            return LABEL_DEFAULT;
        }

//        return !this.isGroupMatch() && this.isUndecidedResult() && this.isPenaltyWinnerOneMatch() ? LABEL_INFO_PENALTY : LABEL_INFO;
        return LABEL_INFO;
    }

    public String getTeamResultTwoCssClasses() {
        if (this.getTeamResultTwo() == null) {
            return LABEL_DEFAULT;
        }

//        return !this.isGroupMatch() && this.isUndecidedResult() && !this.isPenaltyWinnerOneMatch() ? LABEL_INFO_PENALTY : LABEL_INFO;
        return LABEL_INFO;
    }

//    public boolean isJoker() {
//        return joker;
//    }
//
//    public void setJoker(boolean joker) {
//        this.joker = joker;
//    }

	public double getWinOdds() {
		return winOdds;
	}

	public void setWinOdds(double winOdds) {
		this.winOdds = winOdds;
	}

	public double getDrawOdds() {
		return drawOdds;
	}

	public void setDrawOdds(double drawOdds) {
		this.drawOdds = drawOdds;
	}

	public double getLoseOdds() {
		return loseOdds;
	}

	public void setLoseOdds(double loseOdds) {
		this.loseOdds = loseOdds;
	}
    
    
}
