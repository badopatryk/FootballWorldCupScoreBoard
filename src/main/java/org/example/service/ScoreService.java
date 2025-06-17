package org.example.service;

import org.example.model.GameModel;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
    public void updateScore(GameModel game, int newHomeTeamGoals, int newAwayTeamGoals) {

        validateNewScore(game, newHomeTeamGoals, newAwayTeamGoals);

        game.setScore(newHomeTeamGoals, newAwayTeamGoals);
    }

    private void validateNewScore(GameModel game, int newHomeTeamGoals, int newAwayTeamGoals) {

        if (newHomeTeamGoals < 0 || newAwayTeamGoals < 0) {
            throw new IllegalArgumentException("New score cannot be lower than zero!");
        }
        if (newHomeTeamGoals < game.getHomeTeamGoals() || newAwayTeamGoals < game.getAwayTeamGoals()) {
            throw new IllegalArgumentException("New score cannot be lower than existing one!");
        }
    }
}
