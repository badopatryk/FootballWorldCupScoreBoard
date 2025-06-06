package org.example.repository;

import org.example.model.GameModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GameRepositoryImpl implements GameRepository {
    private final List<GameModel> games = new ArrayList<>();

    @Override
    public void save(GameModel game) {
        games.add(game);
    }

    @Override
    public void remove(GameModel game) {
        games.remove(game);
    }

    @Override
    public Optional<GameModel> findByIndex(int index) {
        return Optional.ofNullable(games.get(index));
    }

    @Override
    public Optional<GameModel> find(GameModel game) {
        return games.stream()
                .filter(g -> g.equals(game))
                .findFirst();
    }

    @Override
    public List<GameModel> findAll() {
        return games;
    }

    @Override
    public List<GameModel> getAllGamesSortedByScoreAndTimestamp() {
        return games.stream()
                .sorted(Comparator
                        .comparing(GameModel::getTotalScore, Comparator.reverseOrder())
                        .thenComparing(GameModel::getTimestamp, Comparator.reverseOrder()))
                .toList();
    }
}
