package org.example.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class GameModel {
    private final String homeTeam;
    private final String awayTeam;
    private int homeTeamGoals;
    private int awayTeamGoals;
    private final LocalDateTime timestamp;

    public GameModel(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamGoals = 0;
        this.awayTeamGoals = 0;
        this.timestamp = LocalDateTime.now();
    }

    public void updateScore(int newHomeTeamGoals, int newAwayTeamGoals){
        validateNewScore(newHomeTeamGoals, newAwayTeamGoals);

        this.homeTeamGoals = newHomeTeamGoals;
        this.awayTeamGoals = newAwayTeamGoals;
    }

    private void validateNewScore(int newHomeTeamGoals, int newAwayTeamGoals) {
        if(newHomeTeamGoals < 0 || newAwayTeamGoals < 0){
            throw new IllegalArgumentException("New score cannot be lower than zero!");
        }
        if(newHomeTeamGoals < this.homeTeamGoals || newAwayTeamGoals < this.awayTeamGoals){
            throw new IllegalArgumentException("New score cannot be lower than existing one!");
        }
    }

    private void validateTeamNames(String homeTeam, String awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException("Home and away teams have to be present!");
        }
        if (homeTeam.isBlank() || awayTeam.isBlank()) {
            throw new IllegalArgumentException("Home and away teams cannot be blank!");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Home and away teams must be different!");
        }
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
