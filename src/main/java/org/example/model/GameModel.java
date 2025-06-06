package org.example.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class GameModel {
    private final TeamModel homeTeam;
    private final TeamModel awayTeam;
    private int homeTeamGoals;
    private int awayTeamGoals;
    private LocalDateTime timestamp;

    public GameModel(TeamModel homeTeam, TeamModel awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamGoals = 0;
        this.awayTeamGoals = 0;
    }

    public void setTimestamp() {
        this.timestamp = LocalDateTime.now();
    }

    public void setScore(int homeTeamGoals, int awayTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
    }

    public int getTotalScore() {
        return this.homeTeamGoals + this.awayTeamGoals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameModel gameModel = (GameModel) o;
        return Objects.equals(homeTeam, gameModel.homeTeam) && Objects.equals(awayTeam, gameModel.awayTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam);
    }

    @Override
    public String toString() {
        return homeTeam + " " + homeTeamGoals + " - " + awayTeam + " " + awayTeamGoals;
    }
}
