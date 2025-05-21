package org.example.service;

import org.example.model.GameModel;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardServiceTest{

    @Test
    void gameShouldStart() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);
        Optional<GameModel> possibleGame = scoreBoardService.findGame(game);
        assertTrue(possibleGame.isPresent());
    }
    @Test
    void gameShouldFinish(){
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);

        Optional<GameModel> possibleGame = scoreBoardService.findGame(game);
        assertTrue(possibleGame.isPresent());

        scoreBoardService.finishGame(game);
        Optional<GameModel> possibleGameAfterFinishing = scoreBoardService.findGame(game);

        assertFalse(possibleGameAfterFinishing.isPresent());
    }
    @Test
    void gameShouldUpdate(){
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);
        scoreBoardService.updateGame(game, 2, 1);

        Optional<GameModel> possibleGame = scoreBoardService.findGame(game);

        assertTrue(possibleGame.isPresent());

        GameModel presentGame = possibleGame.get();

        assertTrue(presentGame.getHomeTeamGoals() == 2 && presentGame.getAwayTeamGoals() == 1);
    }
    @Test
    void getSummaryShouldShowAllGames(){
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);
        GameModel firstGameInSummary = scoreBoardService.getSummary().getFirst();

        assertEquals("1. Mexico 0 - Argentina 0", "1. " + firstGameInSummary.toString());
    }
}