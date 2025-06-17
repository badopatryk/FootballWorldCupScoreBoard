package org.example.service;

import org.example.model.GameModel;
import org.example.model.TeamModel;
import org.example.repository.GameRepository;
import org.example.repository.GameRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {
    private GameService gameService;

    @BeforeEach
    void setUp() {
        GameRepository gameRepository = new GameRepositoryImpl();
        ScoreService scoreService = new ScoreService();
        TeamService teamService = new TeamService();
        gameService = new GameService(gameRepository, scoreService, teamService);
    }

    @Test
    void createGameShouldPreventInvalidTeamNames() {
        assertThrows(IllegalArgumentException.class, () -> gameService.createGame(new TeamModel(null), new TeamModel("Argentina")));
        assertThrows(IllegalArgumentException.class, () -> gameService.createGame(new TeamModel("Mexico"), new TeamModel(null)));
        assertThrows(IllegalArgumentException.class, () -> gameService.createGame(new TeamModel("  "), new TeamModel("Argentina")));
        assertThrows(IllegalArgumentException.class, () -> gameService.createGame(new TeamModel("Argentina"), new TeamModel("Argentina")));
    }

    @Test
    void timestampShouldBeNullBeforeGameStarts() {
        GameModel game = new GameModel(new TeamModel("Mexico"), new TeamModel("Argentina"));

        assertNull(game.getTimestamp());
    }

    @Test
    void gameShouldStart() {
        GameModel game = new GameModel(new TeamModel("Mexico"), new TeamModel("Argentina"));

        gameService.startGame(game);

        assertTrue(gameService.isGameOngoing(game));
    }

    @Test
    void gameShouldFinish() {
        GameModel game = new GameModel(new TeamModel("Mexico"), new TeamModel("Argentina"));

        gameService.startGame(game);
        gameService.finishGame(game.getId());

        assertFalse(gameService.isGameOngoing(game));
    }

    @Test
    void gameShouldUpdate() {
        GameModel game = new GameModel(new TeamModel("Mexico"), new TeamModel("Argentina"));

        gameService.startGame(game);
        gameService.updateGame(game.getId(), 2, 1);

        assertEquals(2, game.getHomeTeamGoals());
        assertEquals(1, game.getAwayTeamGoals());
    }

    @Test
    void getSummaryShouldShowAllGames() {
        GameModel game = new GameModel(new TeamModel("Mexico"), new TeamModel("Argentina"));

        gameService.startGame(game);
        GameModel firstGameInSummary = gameService.getSummary().get(0);

        assertEquals("Mexico 0 - Argentina 0", firstGameInSummary.toString());
    }

    @Test
    void summaryShouldBeEmptyWhenNoGamesStarted() {
        assertTrue(gameService.getSummary().isEmpty());
    }

    @Test
    void shouldNotAllowDuplicateGames() {
        GameModel game = new GameModel(new TeamModel("Mexico"), new TeamModel("Argentina"));

        gameService.startGame(game);

        assertThrows(IllegalArgumentException.class, () -> gameService.startGame(new GameModel(new TeamModel("Mexico"), new TeamModel("Argentina"))));
    }

    @Test
    void shouldNotUpdateNotStartedGame() {
        GameModel game = new GameModel(new TeamModel("Mexico"), new TeamModel("Argentina"));

        assertThrows(IllegalArgumentException.class, () -> gameService.updateGame(game.getId(), 2, 1));
    }

    @Test
    void getSummaryShouldReturnSortedGames() throws InterruptedException {
        GameModel game1 = new GameModel(new TeamModel("Mexico"), new TeamModel("Argentina"));
        GameModel game2 = new GameModel(new TeamModel("Canada"), new TeamModel("Poland"));
        GameModel game3 = new GameModel(new TeamModel("Venezuela"), new TeamModel("Bhutan"));

        gameService.startGame(game1);
        Thread.sleep(1);
        gameService.startGame(game2);
        Thread.sleep(1);
        gameService.startGame(game3);

        gameService.updateGame(game2.getId(), 2, 1);
        gameService.updateGame(game3.getId(), 1, 2);

        List<GameModel> summary = gameService.getSummary();

        assertEquals(new TeamModel("Venezuela"), summary.get(0).getHomeTeam());
        assertEquals(new TeamModel("Canada"), summary.get(1).getHomeTeam());
        assertEquals(new TeamModel("Mexico"), summary.get(2).getHomeTeam());
    }

    @Test
    void shouldNotFinishNotStartedGame() {
        GameModel game = new GameModel(new TeamModel("Mexico"), new TeamModel("Argentina"));

        assertThrows(IllegalArgumentException.class, () -> gameService.finishGame(game.getId()));
    }

    @Test
    void shouldPreventNewLowerScore() {
        GameModel game = new GameModel(new TeamModel("Mexico"), new TeamModel("Argentina"));

        gameService.startGame(game);
        gameService.updateGame(game.getId(), 2, 1);

        assertThrows(IllegalArgumentException.class, () -> gameService.updateGame(game.getId(), 1, 1));
    }

    @Test
    void shouldAllowSameScoreAgain() {
        GameModel game = new GameModel(new TeamModel("Mexico"), new TeamModel("Argentina"));

        gameService.startGame(game);
        gameService.updateGame(game.getId(), 2, 2);

        assertDoesNotThrow(() -> gameService.updateGame(game.getId(), 2, 2));
    }

    @Test
    void shouldSetTimestampWhenGameStarts() {
        GameModel game = new GameModel(new TeamModel("Mexico"), new TeamModel("Argentina"));

        gameService.startGame(game);

        assertNotNull(game.getTimestamp());
    }
}