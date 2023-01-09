package de.fred4jupiter.fredbet.repository;

import de.fred4jupiter.fredbet.domain.Match;

public class PointCourseResult {

    private final String username;

    private final Double points;

    private final Match match;

    public PointCourseResult(String username, Double points, Match match) {
        this.username = username;
        this.points = points;
        this.match = match;
    }

    public String getUsername() {
        return username;
    }

    public Double getPoints() {
        return points;
    }

    public Match getMatch() {
        return match;
    }
}
