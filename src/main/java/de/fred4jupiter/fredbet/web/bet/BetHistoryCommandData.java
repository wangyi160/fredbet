package de.fred4jupiter.fredbet.web.bet;

import java.time.LocalDateTime;

public class BetHistoryCommandData {

    private LocalDateTime createdAt;

    private String matchGroup;

    private String teamNameOne;

    private String teamNameTwo;

    private String betType;
    private double odds;
    private Double points;

    private Double reward;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getMatchGroup() {
        return matchGroup;
    }

    public void setMatchGroup(String matchGroup) {
        this.matchGroup = matchGroup;
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

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public Double getReward() {
        return reward;
    }

    public void setReward(Double reward) {
        this.reward = reward;
    }
}
