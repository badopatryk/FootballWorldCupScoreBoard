package org.example.model.dto;

import org.example.model.TeamModel;

public record NewGameDto(TeamModel homeTeam, TeamModel awayTeam) {
}
