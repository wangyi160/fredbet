package de.fred4jupiter.fredbet.repository;

public class PointCourseResultSimple {

    private final Long matchId;

    private final String username;

    private final Double points;

    public PointCourseResultSimple(Long matchId, String username, Double points) {
        this.matchId = matchId;
        this.username = username;
        this.points = points;
    }

    public Long getMatchId() {
        return matchId;
    }

    public String getUsername() {
        return username;
    }

    public Double getPoints() {
        return points;
    }
}
