package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.GameModel;
import org.example.model.TeamModel;
import org.example.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final ScoreService scoreService;
    private final TeamService teamService;

    public GameModel createGame(TeamModel homeTeam, TeamModel awayTeam) {

        teamService.validateTeamNames(homeTeam, awayTeam);
        return new GameModel(homeTeam, awayTeam);
    }

    public void startGame(GameModel game) {

        if (isGameOngoing(game))
            throw new IllegalArgumentException("Game has already started!");

        game.setTimestamp();
        gameRepository.save(game);
    }

    public void finishGame(int gameId) {

        if (gameRepository.find(gameId).isEmpty())
            throw new IllegalArgumentException("Game has not started yet!");

        gameRepository.remove(gameId);
    }

    public void updateGame(int gameId, int homeTeamScore, int awayTeamScore) {

        GameModel game = getExistingGameOrThrow(gameId);

        scoreService.updateScore(game, homeTeamScore, awayTeamScore);
    }

    public List<GameModel> getSummary() {

        return gameRepository.getAllGamesSortedByScoreAndTimestamp();
    }

    public boolean isGameOngoing(GameModel game) {

        return gameRepository.findAll().stream()
                .anyMatch(g -> g.equals(game));
    }

    public List<GameModel> findAll() {

        return gameRepository.findAll();
    }
    public GameModel getExistingGameOrThrow(int gameId){
        return gameRepository.find(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game with ID " + gameId + " does not exist."));
    }

    // outdated
    public Optional<GameModel> findGameByIndex(int index) {

        return gameRepository.findByIndex(index);
    }

}
