package org.example.repository;

import org.example.model.GameModel;

import java.util.List;
import java.util.Optional;

public interface GameRepository {
    void save(GameModel game);

    void remove(int gameId);

    Optional<GameModel> findByIndex(int index);
    Optional<GameModel> find(int gameId);

    List<GameModel> findAll();
    List<GameModel> getAllGamesSortedByScoreAndTimestamp();
}
