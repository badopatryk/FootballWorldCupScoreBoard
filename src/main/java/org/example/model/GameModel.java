package org.example.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
public class GameModel {
    @NonNull
    private String homeTeam;
    @NonNull
    private String awayTeam;
    private int homeTeamGoals = 0;
    private int awayTeamGoals = 0;
    private final LocalDateTime timestamp = LocalDateTime.now();
}
