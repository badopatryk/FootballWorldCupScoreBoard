package org.example.service;

import org.example.model.TeamModel;

public class TeamService {
    public void validateTeamNames(TeamModel homeTeam, TeamModel awayTeam) {
        if (!(homeTeam.name().matches("[a-zA-Z]+") && awayTeam.name().matches("[a-zA-Z]+"))) {
            throw new IllegalArgumentException("Home and away teams have to be strings!");
        }
        if (homeTeam.name().isBlank() || awayTeam.name().isBlank()) {
            throw new IllegalArgumentException("Home and away teams cannot be blank!");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Home and away teams must be different!");
        }
    }
}
