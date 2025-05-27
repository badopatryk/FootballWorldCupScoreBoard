package org.example.service;

import org.example.model.GameModel;

import java.util.*;

public class ScoreBoardService {
    private HashSet<GameModel> games = new HashSet<>();

    public void startGame(GameModel game) {
        if (isGameStarted(game))
            throw new IllegalArgumentException("Game has already started!");

        game.setTimestamp();
        games.add(game);
    }

    public void finishGame(GameModel game) {
        if (!isGameStarted(game))
            throw new IllegalArgumentException("Game has not started yet!");

        games.remove(game);
    }

    public void updateGame(GameModel game, int homeTeamScore, int awayTeamScore) {
        if (!isGameStarted(game))
            throw new IllegalArgumentException("You cannot edit a game that has not started yet!");

        game.updateScore(homeTeamScore, awayTeamScore);
    }

    public List<GameModel> getSummary() {
        return games.stream()
                .sorted(Comparator
                        .comparing(GameModel::getTotalScore, Comparator.reverseOrder())
                        .thenComparing(GameModel::getTimestamp, Comparator.reverseOrder()))
                .toList();
    }

    public boolean isGameStarted(GameModel game) {
        return games.contains(game);
    }
}
