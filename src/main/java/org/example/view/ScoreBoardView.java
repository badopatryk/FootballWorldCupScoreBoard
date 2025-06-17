package org.example.view;

import lombok.AllArgsConstructor;
import org.example.model.GameModel;
import org.example.model.TeamModel;
import org.example.service.GameService;

import java.util.Optional;
import java.util.Scanner;

@AllArgsConstructor
public class ScoreBoardView {
    private GameService gameService;

    public void runScoreBoard() {

        Scanner scanner = new Scanner(System.in);

        showPossibleActions();

        while (true) {
            Integer i = takeInput(scanner, "Please enter the next action:", Integer.class);

            if (i == 0) {
                System.out.println("Goodbye.");
                break;
            }
            performAction(scanner, i);
        }
    }

    private void performAction(Scanner scanner, int i) {

        switch (i) {
            case 1 -> startNewGame(scanner);
            case 2 -> updateScore(scanner);
            case 3 -> printSummary();
            case 4 -> finishSelectedGame(scanner);
            default -> System.out.println("Incorrect value, try again");
        }
    }

    private void updateScore(Scanner scanner) {

        showAllCurrentGames();

        Integer id = takeInput(scanner, "Which ongoing game's score you want to update?", Integer.class);

        Integer homeTeamNewScore = takeInput(scanner, "Please type new home team score:", Integer.class);
        Integer awayTeamNewScore = takeInput(scanner, "Please type new away team score:", Integer.class);

        Optional<GameModel> gameById = gameService.findGameByIndex(id - 1);

        if (gameById.isEmpty()) {
            System.out.println("Sorry, game not found");
        } else {
            try {
                GameModel game = gameById.get();
                gameService.updateGame(game.getId(), homeTeamNewScore, awayTeamNewScore);
            } catch (IllegalArgumentException e) {
                System.out.println("Failed to update the game: " + e.getMessage());
            }
        }
    }

    private void startNewGame(Scanner scanner) {

        String homeTeamName = takeInput(scanner, "Please enter home team:", String.class);
        String awayTeamName = takeInput(scanner, "Please enter away team:", String.class);

        try {
            GameModel game = gameService.createGame(new TeamModel(homeTeamName), new TeamModel(awayTeamName));
            gameService.startGame(game);

        } catch (IllegalArgumentException e) {
            System.out.println("Failed to start game: " + e.getMessage());
        }
    }

    private void finishSelectedGame(Scanner scanner) {

        showAllCurrentGames();

        Integer id = takeInput(scanner, "Which ongoing game do you want to finish?", Integer.class);

        Optional<GameModel> gameById = gameService.findGameByIndex(id - 1);

        if (gameById.isEmpty()) {
            System.out.println("Sorry, game not found");
        } else {
            try {
                GameModel game = gameById.get();
                gameService.finishGame(game.getId());
            } catch (IllegalArgumentException e) {
                System.out.println("Failed to finish game: " + e.getMessage());
            }
        }
    }

    private void printSummary() {

        gameService.getSummary().forEach(System.out::println);
    }

    private void showPossibleActions() {

        System.out.println("1. Start game");
        System.out.println("2. Update score");
        System.out.println("3. Get summary");
        System.out.println("4. Finish game");
    }

    private <T> T takeInput(Scanner scanner, String message, Class<T> type) {

        while (true) {
            System.out.println(message);
            String line = scanner.nextLine();

            try {
                if (type == Integer.class) return type.cast(Integer.parseInt(line));
                if (type == String.class) return type.cast(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input format. Please try again.");
            }
        }
    }

    private void showAllCurrentGames() {

        int index = 1;
        for (GameModel game : gameService.findAll()) {
            System.out.println(index + ". " + game);
            index++;
        }
    }
}
