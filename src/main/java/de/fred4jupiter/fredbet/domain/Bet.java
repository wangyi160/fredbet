package de.fred4jupiter.fredbet.domain;

import de.fred4jupiter.fredbet.props.FredbetConstants;
import de.fred4jupiter.fredbet.service.ranking.Visitor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.persistence.*;

@Entity
@Table(name = "BET")
public class Bet implements Visitable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BET_ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @ManyToOne(targetEntity = Match.class)
    @JoinColumn(name = "MATCH_ID")
    private Match match;

//    private Integer goalsTeamOne;
//
//    private Integer goalsTeamTwo;

    private Double points ;

//    @Column(name = "PENALTY_WINNER_ONE")
//    private boolean penaltyWinnerOne;
//
//    @Column(name = "JOKER")
//    private boolean joker;
    
    @Column(name = "BET_TYPE")
	private String betType;
    
    @Column(name = "ODDS")
	private double odds;
    
    @Column(name = "REWARD")
	private double reward;
    
    

//    public Integer getGoalDifference() {
//        if (goalsTeamOne == null || goalsTeamTwo == null) {
//            throw new IllegalStateException("Bet not finished! No goal bets set!");
//        }
//        return Math.abs(goalsTeamOne - goalsTeamTwo);
//    }
//
//    public boolean isTeamOneWinner() {
//        if (goalsTeamOne == null || goalsTeamTwo == null) {
//            throw new IllegalStateException("Bet not finished! No goal bets set!");
//        }
//
//        return goalsTeamOne > goalsTeamTwo;
//    }
//
//    public boolean isTeamTwoWinner() {
//        if (goalsTeamOne == null || goalsTeamTwo == null) {
//            throw new IllegalStateException("Bet not finished! No goal bets set!");
//        }
//
//        return goalsTeamTwo > goalsTeamOne;
//    }

//    public boolean isUndecidedBetting() {
//        return getGoalDifference() == 0;
//    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Bet bet = (Bet) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(id, bet.id);
        builder.append(userName, bet.userName);
        builder.append(match, bet.match);
//        builder.append(goalsTeamOne, bet.goalsTeamOne);
//        builder.append(goalsTeamTwo, bet.goalsTeamTwo);
        builder.append(points, bet.points);
        
        builder.append(betType, bet.betType);
        builder.append(odds, bet.odds);
        builder.append(reward, bet.reward);
        
//        builder.append(penaltyWinnerOne, bet.penaltyWinnerOne);
//        builder.append(joker, bet.joker);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(id);
        builder.append(userName);
        builder.append(match);
//        builder.append(goalsTeamOne);
//        builder.append(goalsTeamTwo);
        builder.append(points);
        
        builder.append(betType);
        builder.append(odds);
        builder.append(reward);
        
//        builder.append(penaltyWinnerOne);
//        builder.append(joker);
        return builder.toHashCode();
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        builder.append("id", id);
        builder.append("userName", userName);
        builder.append("match", match);
//        builder.append("goalsTeamOne", goalsTeamOne);
//        builder.append("goalsTeamTwo", goalsTeamTwo);
        builder.append("points", points);
        
        builder.append("betType", betType);
        builder.append("odds", odds);
        builder.append("reward", reward);
        
//        builder.append("penaltyWinnerOne", penaltyWinnerOne);
//        builder.append("joker", joker);
        return builder.toString();
    }

//    public Integer getGoalsTeamOne() {
//        return goalsTeamOne;
//    }
//
//    public void setGoalsTeamOne(Integer goalsTeamOne) {
//        this.goalsTeamOne = goalsTeamOne;
//    }
//
//    public Integer getGoalsTeamTwo() {
//        return goalsTeamTwo;
//    }
//
//    public void setGoalsTeamTwo(Integer goalsTeamTwo) {
//        this.goalsTeamTwo = goalsTeamTwo;
//    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Match getMatch() {
        return match;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

//    public boolean isPenaltyWinnerOne() {
//        return penaltyWinnerOne;
//    }

    private boolean isGroupMatch() {
        return this.match.isGroupMatch();
    }

//    public void setPenaltyWinnerOne(boolean penaltyWinnerOne) {
//        this.penaltyWinnerOne = penaltyWinnerOne;
//    }
//
//    public String getCssClassPenaltyWinnerOne() {
//        String cssClasses = "";
//        if (!this.isGroupMatch() && this.isUndecidedBetting() && this.isPenaltyWinnerOne()) {
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
//    public String getCssClassPenaltyWinnerTwo() {
//        String cssClasses = "";
//        if (!this.isGroupMatch() && this.isUndecidedBetting() && !this.isPenaltyWinnerOne()) {
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
//    public boolean isJoker() {
//        return joker;
//    }
//
//    public void setJoker(boolean joker) {
//        this.joker = joker;
//    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public boolean isCorrectResult() {
//        if (goalsTeamOne != null && goalsTeamTwo != null && match != null && match.getGoalsTeamOne() != null && match.getGoalsTeamTwo() != null) {
//            return goalsTeamOne.equals(match.getGoalsTeamOne()) && goalsTeamTwo.equals(match.getGoalsTeamTwo());
//        }
//        
//        return false;
    	
    	if(betType.equals("win") && match.getGoalsTeamOne() > match.getGoalsTeamTwo()) {
    		return true;
    	}
    	
    	if(betType.equals("draw") && match.getGoalsTeamOne() == match.getGoalsTeamTwo()) {
    		return true;
    	}
    	
    	if(betType.equals("lose") && match.getGoalsTeamOne() < match.getGoalsTeamTwo()) {
    		return true;
    	}
    	
    	return false;
    	
    }

	public String getBetType() {
		return betType;
	}

	public void setBetType(String betType) {
		this.betType = betType;
	}

	public double getOdds() {
		return odds;
	}

	public void setOdds(double odds) {
		this.odds = odds;
	}

	public double getReward() {
		return reward;
	}

	public void setReward(double reward) {
		this.reward = reward;
	}
    
    
}
