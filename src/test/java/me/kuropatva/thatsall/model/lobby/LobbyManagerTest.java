package me.kuropatva.thatsall.model.lobby;

import me.kuropatva.thatsall.model.player.Player;
import me.kuropatva.thatsall.model.player.PlayerCredentials;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LobbyManagerTest {

    @Test
    void newGame() {
        var a = LobbyManager.newGame("a");
        assertNotNull(a);
        assertEquals(0, a.playerSize());
        assertNull(LobbyManager.newGame(null));
    }

    @Test
    void game() {
        var a = LobbyManager.newGame("a");
        assertEquals(a, LobbyManager.game("a"));
        assertNull(LobbyManager.game(null));
        assertDoesNotThrow(() -> LobbyManager.game(null));
    }

    @Test
    void close() {
        var a = LobbyManager.newGame("a");
        a.addPlayer(new Player(new PlayerCredentials("a", "b")));
        assertEquals(1, a.playerSize());
        LobbyManager.close(a);
        assertEquals(0, a.playerSize());
        assertDoesNotThrow(() -> LobbyManager.close(null));
    }
}