package org.example.service;

import org.example.model.GameModel;

import java.util.*;
import java.util.stream.Stream;

public class ScoreBoardService {
    private HashSet<GameModel> games = new HashSet<>();

    public void startGame(GameModel game) {
        if (checkIfGameStarted(game))
            throw new IllegalArgumentException("Game has already started!");

        games.add(game);
    }

    public void finishGame(GameModel game) {
        if (!checkIfGameStarted(game))
            throw new IllegalArgumentException("Game has not started yet!");

        games.remove(game);
    }

    public void updateGame(GameModel game, int homeTeamScore, int awayTeamScore) {
        if (!checkIfGameStarted(game))
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

    public Optional<GameModel> findGame(GameModel game) {
        return games.stream()
                .filter(a -> a.equals(game))
                .findFirst();
    }

    private boolean checkIfGameStarted(GameModel game) {
        return games.contains(game);
    }
}
