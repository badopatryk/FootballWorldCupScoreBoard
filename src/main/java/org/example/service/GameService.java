package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.GameModel;
import org.example.model.TeamModel;
import org.example.repository.GameRepository;

import java.util.List;
import java.util.Optional;

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

    public void finishGame(GameModel game) {
        if (!isGameOngoing(game))
            throw new IllegalArgumentException("Game has not started yet!");

        gameRepository.remove(game);
    }

    public void updateGame(GameModel game, int homeTeamScore, int awayTeamScore) {
        if (!isGameOngoing(game))
            throw new IllegalArgumentException("You cannot edit a game that has not started yet!");

        scoreService.updateScore(game, homeTeamScore, awayTeamScore);
    }

    public List<GameModel> getSummary() {
        return gameRepository.getAllGamesSortedByScoreAndTimestamp();
    }

    public boolean isGameOngoing(GameModel game) {
        return gameRepository.find(game).isPresent();
    }
    public Optional<GameModel> findGameByIndex(int id){
        return gameRepository.findByIndex(id);
    }
    public List<GameModel> findAll(){
        return gameRepository.findAll();
    }
}
