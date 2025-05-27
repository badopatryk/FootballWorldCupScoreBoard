package org.example.service;

import org.example.model.GameModel;
import org.example.repository.GameRepository;
import org.example.repository.GameRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardServiceTest {
    private ScoreBoardService scoreBoardService;

    @BeforeEach
    void setUp() {
        GameRepository gameRepository = new GameRepositoryImpl();
        scoreBoardService = new ScoreBoardService(gameRepository);
    }

    @Test
    void gameShouldStart() {
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);

        assertTrue(scoreBoardService.isGameStarted(game));
    }

    @Test
    void gameShouldFinish() {
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);
        scoreBoardService.finishGame(game);

        assertFalse(scoreBoardService.isGameStarted(game));
    }

    @Test
    void gameShouldUpdate() {
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);
        scoreBoardService.updateGame(game, 2, 1);

        assertEquals(2, game.getHomeTeamGoals());
        assertEquals(1, game.getAwayTeamGoals());
    }

    @Test
    void getSummaryShouldShowAllGames() {
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);
        GameModel firstGameInSummary = scoreBoardService.getSummary().getFirst();

        assertEquals("Mexico 0 - Argentina 0", firstGameInSummary.toString());
    }

    @Test
    void summaryShouldBeEmptyWhenNoGamesStarted() {
        assertTrue(scoreBoardService.getSummary().isEmpty());
    }

    @Test
    void shouldNotAllowDuplicateGames() {
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);

        assertThrows(IllegalArgumentException.class, () -> scoreBoardService.startGame(new GameModel("Mexico", "Argentina")));
    }

    @Test
    void shouldNotUpdateNotStartedGame() {
        GameModel game = new GameModel("Mexico", "Argentina");

        assertThrows(IllegalArgumentException.class, () -> scoreBoardService.updateGame(game, 2, 1));
    }

    @Test
    void getSummaryShouldReturnSortedGames() throws InterruptedException {
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
        GameModel game = new GameModel("Mexico", "Argentina");

        assertThrows(IllegalArgumentException.class, () -> scoreBoardService.finishGame(game));
    }

    @Test
    void shouldPreventNewLowerScore() {
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);
        scoreBoardService.updateGame(game, 2, 1);

        assertThrows(IllegalArgumentException.class, () -> scoreBoardService.updateGame(game, 1, 1));
    }

    @Test
    void shouldAllowSameScoreAgain() {
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);
        scoreBoardService.updateGame(game, 2, 2);

        assertDoesNotThrow(() -> scoreBoardService.updateGame(game, 2, 2));
    }

    @Test
    void shouldSetTimestampWhenGameStarts() {
        GameModel game = new GameModel("Mexico", "Argentina");

        scoreBoardService.startGame(game);

        assertNotNull(game.getTimestamp());
    }
}