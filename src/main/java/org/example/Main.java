package org.example;

import org.example.repository.GameRepositoryImpl;
import org.example.service.GameService;
import org.example.service.ScoreService;
import org.example.service.TeamService;
import org.example.view.ScoreBoardView;

public class Main {
    public static void main(String[] args) {
        GameService gameService = new GameService(new GameRepositoryImpl(), new ScoreService(), new TeamService());

        ScoreBoardView scoreBoardView = new ScoreBoardView(gameService);
        scoreBoardView.runScoreBoard();
    }
}