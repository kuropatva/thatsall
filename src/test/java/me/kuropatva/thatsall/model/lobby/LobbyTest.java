package me.kuropatva.thatsall.model.lobby;

import me.kuropatva.thatsall.model.player.Player;
import me.kuropatva.thatsall.model.player.PlayerCredentials;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class LobbyTest {

    @Test
    void setPasswordAndAuthorize() {
        var lobby = new Lobby();
        lobby.setPassword("123");
        assertTrue(lobby.authorize("123"));
        assertFalse(lobby.authorize(null));
        assertFalse(lobby.authorize(""));
        assertFalse(lobby.authorize("1"));

        lobby = new Lobby();
        assertTrue(lobby.authorize(null));
        assertTrue(lobby.authorize("123"));
        assertTrue(lobby.authorize(""));
    }

    @Test
    void hasPlayerNameAndAdd() {
        var lobby = new Lobby();
        var testPlayer = new Player(new PlayerCredentials("test", "test2"));
        lobby.addPlayer(testPlayer);
        assertTrue(lobby.hasPlayerName("test"));
        assertFalse(lobby.hasPlayerName("123"));
        assertFalse(lobby.hasPlayerName(null));
        assertFalse(lobby.hasPlayerName(""));
    }

    @Test
    void playerSize() {
        var lobby = new Lobby();
        var testPlayer = new Player(new PlayerCredentials("test", "test2"));
        assertEquals(0, lobby.playerSize());
        lobby.addPlayer(testPlayer);
        assertEquals(1, lobby.playerSize());
    }

    @Test
    void game() {
        var lobby = new Lobby();
        assertNotNull(lobby.game());
    }

    @Test
    void getPlayer() {
        var lobby = new Lobby();
        var testPlayer = new Player(new PlayerCredentials("test", "test2"));
        lobby.addPlayer(testPlayer);
        assertEquals(testPlayer, lobby.getPlayer("test"));
        assertNull(lobby.getPlayer(null));
    }

    @Test
    void players() {
        var lobby = new Lobby();
        var testPlayer = new Player(new PlayerCredentials("test", "test2"));
        lobby.addPlayer(testPlayer);
        var it = lobby.players().iterator();
        assertEquals(testPlayer, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    void getGameSocketHandler() {
        var lobby = new Lobby();
        assertNotNull(lobby.getGameSocketHandler());
    }

    @Test
    void close() {
        var lobby = new Lobby();
        var testPlayer = new Player(new PlayerCredentials("test", "test2"));
        lobby.addPlayer(testPlayer);
        lobby.close();
        assertEquals(0, lobby.playerSize());
    }

    @Test
    void lobbyID() {
        var i1 = LobbyIDGenerator.get();
        var i2 = LobbyIDGenerator.get();
        assertNotEquals(i1, i2);
    }
}