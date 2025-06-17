package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.model.GameModel;
import org.example.model.dto.NewGameDto;
import org.example.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
@AllArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping("/start")
    public ResponseEntity<String> startGame(@RequestBody NewGameDto newGameDto) {

        try {
            gameService.startGame(new GameModel(newGameDto.homeTeam(), newGameDto.awayTeam()));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Game started");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateGame(@RequestParam int gameId, @RequestParam int newHomeTeamScore, @RequestParam int newAwayTeamScore) {

        try {
            gameService.updateGame(gameId, newHomeTeamScore, newAwayTeamScore);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Game updated");
    }
}