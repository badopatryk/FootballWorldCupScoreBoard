package org.example.service;

import org.example.model.GameModel;

import java.util.List;
import java.util.Optional;

public class ScoreBoardService {
    public void startGame(GameModel game){}
    public void finishGame(GameModel game){}
    public void updateGame(GameModel game, int homeTeamScore, int awayTeamScore){}
    public List<GameModel> getSummary(){
        return null;
    }

    public Optional<GameModel> findGame(GameModel game) {
        return Optional.empty();
    }
}
