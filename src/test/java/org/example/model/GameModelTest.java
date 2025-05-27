package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {
    @Test
    void shouldPreventInvalidTeamNames() {
        assertThrows(IllegalArgumentException.class, () -> new GameModel(null, "Argentina"));
        assertThrows(IllegalArgumentException.class, () -> new GameModel("Mexico", null));
        assertThrows(IllegalArgumentException.class, () -> new GameModel("  ", "Argentina"));
        assertThrows(IllegalArgumentException.class, () -> new GameModel("Mexico", "Mexico"));
    }

    @Test
    void timestampShouldBeNullBeforeGameStarts() {
        GameModel game = new GameModel("Mexico", "Argentina");

        assertNull(game.getTimestamp());
    }
}