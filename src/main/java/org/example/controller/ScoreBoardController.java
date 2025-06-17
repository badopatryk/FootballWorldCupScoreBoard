package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.model.GameModel;
import org.example.service.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/scoreBoard")
@AllArgsConstructor
public class ScoreBoardController {
    private final GameService gameService;

    @GetMapping("/summary")
    public List<GameModel> getScoreBoard(){
        return gameService.getSummary();
    }
}
