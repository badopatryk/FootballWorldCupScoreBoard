package org.example.service;

import org.example.model.GameModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardServiceTest {

    @Test
    void gameShouldStart() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);

        assertTrue(scoreBoardService.isGameStarted(game));
    }

    @Test
    void gameShouldFinish() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);
        scoreBoardService.finishGame(game);

        assertFalse(scoreBoardService.isGameStarted(game));
    }

    @Test
    void gameShouldUpdate() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);
        scoreBoardService.updateGame(game, 2, 1);

        assertTrue(game.getHomeTeamGoals() == 2 && game.getAwayTeamGoals() == 1);
    }

    @Test
    void getSummaryShouldShowAllGames() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);
        GameModel firstGameInSummary = scoreBoardService.getSummary().getFirst();

        assertEquals("Mexico 0 - Argentina 0", firstGameInSummary.toString());
    }

    @Test
    void summaryShouldBeEmptyWhenNoGamesStarted() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();

        assertTrue(scoreBoardService.getSummary().isEmpty());
    }

    @Test
    void shouldNotAllowDuplicateGames() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);

        assertThrows(IllegalArgumentException.class, () -> scoreBoardService.startGame(new GameModel("Mexico", "Argentina")));
    }

    @Test
    void shouldNotUpdateNotStartedGame() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        GameModel game = new GameModel("Mexico", "Argentina");

        assertThrows(IllegalArgumentException.class, () -> scoreBoardService.updateGame(game, 2, 1));
    }

    @Test
    void getSummaryShouldReturnSortedGames() throws InterruptedException {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        GameModel game1 = new GameModel("Mexico", "Argentina");
        GameModel game2 = new GameModel("Canada", "Poland");
        GameModel game3 = new GameModel("Venezuela", "Bhutan");

        scoreBoardService.startGame(game1);
        Thread.sleep(1);
        scoreBoardService.startGame(game2);
        Thread.sleep(1);
        scoreBoardService.startGame(game3);

        scoreBoardService.updateGame(game2, 2, 1);
        scoreBoardService.updateGame(game3, 1, 2);

        List<GameModel> summary = scoreBoardService.getSummary();

        assertEquals("Venezuela", summary.get(0).getHomeTeam());
        assertEquals("Canada", summary.get(1).getHomeTeam());
        assertEquals("Mexico", summary.get(2).getHomeTeam());
    }

    @Test
    void shouldNotFinishNotStartedGame() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        GameModel game = new GameModel("Mexico", "Argentina");

        assertThrows(IllegalArgumentException.class, () -> scoreBoardService.finishGame(game));
    }

    @Test
    void shouldPreventNewLowerScore() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);
        scoreBoardService.updateGame(game, 2, 1);

        assertThrows(IllegalArgumentException.class, () -> scoreBoardService.updateGame(game, 1, 1));
    }

    @Test
    void shouldRejectInvalidTeamNames() {
        assertThrows(IllegalArgumentException.class, () -> new GameModel(null, "Argentina"));
        assertThrows(IllegalArgumentException.class, () -> new GameModel("Mexico", null));
        assertThrows(IllegalArgumentException.class, () -> new GameModel("  ", "Argentina"));
        assertThrows(IllegalArgumentException.class, () -> new GameModel("Mexico", "Mexico"));
    }

    @Test
    void shouldAllowSameScoreAgain() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);
        scoreBoardService.updateGame(game, 2, 2);

        assertDoesNotThrow(() -> scoreBoardService.updateGame(game, 2, 2));
    }

    @Test
    void timestampShouldBeNullBeforeGameStarts() {
        GameModel game = new GameModel("Mexico", "Argentina");
        assertNull(game.getTimestamp());
    }

    @Test
    void shouldSetTimestampWhenGameStarts() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);

        assertNotNull(game.getTimestamp());
    }
}