package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.GameModel;
import org.example.repository.GameRepository;

import java.util.*;

@AllArgsConstructor
public class ScoreBoardService {
    private final GameRepository gameRepository;

    public void startGame(GameModel game) {
        if (isGameStarted(game))
            throw new IllegalArgumentException("Game has already started!");

        game.setTimestamp();
        gameRepository.save(game);
    }

    public void finishGame(GameModel game) {
        if (!isGameStarted(game))
            throw new IllegalArgumentException("Game has not started yet!");

        gameRepository.remove(game);
    }

    public void updateGame(GameModel game, int homeTeamScore, int awayTeamScore) {
        if (!isGameStarted(game))
            throw new IllegalArgumentException("You cannot edit a game that has not started yet!");

        game.updateScore(homeTeamScore, awayTeamScore);
    }

    public List<GameModel> getSummary() {
        return gameRepository.findAll().stream()
                .sorted(Comparator
                        .comparing(GameModel::getTotalScore, Comparator.reverseOrder())
                        .thenComparing(GameModel::getTimestamp, Comparator.reverseOrder()))
                .toList();
    }

    public boolean isGameStarted(GameModel game) {
        Optional<GameModel> gameModel = gameRepository.find(game);

        return gameModel.isPresent();
    }
}
