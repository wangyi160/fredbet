package de.fred4jupiter.fredbet.repository;

public class PointsPerUser {

    private final String username;

    private final Double points;

    public PointsPerUser(String username, Double points) {
        this.username = username;
        this.points = points;
    }

    public String getUsername() {
        return username;
    }

    public Double getPoints() {
        return points;
    }


}
