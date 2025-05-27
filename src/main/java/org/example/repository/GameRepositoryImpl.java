package org.example.repository;

import org.example.model.GameModel;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class GameRepositoryImpl implements GameRepository {
    private final HashSet<GameModel> games = new HashSet<>();

    @Override
    public void save(GameModel game) {
        games.add(game);
    }

    @Override
    public void remove(GameModel game) {
        games.remove(game);
    }

    @Override
    public Optional<GameModel> find(GameModel game) {
        return games.stream()
                .filter(g -> g.equals(game))
                .findFirst();
    }

    @Override
    public List<GameModel> findAll() {
        return games.stream().toList();
    }
}
