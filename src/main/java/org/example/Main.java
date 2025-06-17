package org.example;

import org.example.model.GameModel;
import org.example.model.TeamModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
//        GameService gameService = new GameService(new GameRepositoryImpl(), new ScoreService(), new TeamService());
//
//        ScoreBoardView scoreBoardView = new ScoreBoardView(gameService);
//        scoreBoardView.runScoreBoard();
        SpringApplication.run(Main.class, args);
    }
}