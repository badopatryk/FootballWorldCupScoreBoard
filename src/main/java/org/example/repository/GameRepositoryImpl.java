package org.example.repository;

import org.example.model.GameModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class GameRepositoryImpl implements GameRepository {
    private final List<GameModel> games = new ArrayList<>();

    @Override
    public void save(GameModel game) {

        games.add(game);
    }

    @Override
    public void remove(int gameId) {

        games.removeIf(g -> g.getId() == gameId);
    }

    @Override
    public Optional<GameModel> findByIndex(int index) {

        return Optional.ofNullable(games.get(index));
    }

    @Override
    public Optional<GameModel> find(int gameId) {

        return games.stream()
                .filter(g -> g.getId() == gameId)
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
