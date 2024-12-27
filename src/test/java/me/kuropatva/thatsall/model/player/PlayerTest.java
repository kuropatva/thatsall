package me.kuropatva.thatsall.model.player;

import me.kuropatva.thatsall.model.lobby.Lobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerTest {

    private Player player;
    private PlayerCredentials playerCredentials;
    private Lobby mockLobby;

    @BeforeEach
    void setUp() {
        playerCredentials = mock(PlayerCredentials.class);
        when(playerCredentials.username()).thenReturn("testUser");
        when(playerCredentials.authorize("1234")).thenReturn(true);
        when(playerCredentials.authorize("wrongPass")).thenReturn(false);

        player = new Player(playerCredentials);
        mockLobby = mock(Lobby.class);
    }

    @Test
    void testGetAndSetLobby() {
        assertNull(player.getLobby(), "Lobby should initially be null");

        player.getLobby(mockLobby);
        assertEquals(mockLobby, player.getLobby(), "Lobby should be set correctly");
    }

    @Test
    void testUsername() {
        assertEquals("testUser", player.username(), "Username should match the one in PlayerCredentials");
    }

    @Test
    void testAuthorize() {
        assertTrue(player.authorize("1234"), "Player should authorize with the correct passcode");
        assertFalse(player.authorize("wrongPass"), "Player should not authorize with an incorrect passcode");
    }

    @Test
    void testAddAndRemoveSession() {
        WebSocketSession mockSession1 = mock(WebSocketSession.class);
        WebSocketSession mockSession2 = mock(WebSocketSession.class);

        player.addSession(mockSession1);
        player.addSession(mockSession2);

        // Verify sessions added
        player.removeSession(mockSession1);
        player.removeSession(mockSession2);

        // Check no errors occurred
    }

    @Test
    void testGamePlayer() {
        assertNotNull(player.gamePlayer(), "GamePlayer instance should not be null");
    }

    @Test
    void testClearSessions() throws IOException {
        WebSocketSession mockSession1 = mock(WebSocketSession.class);
        WebSocketSession mockSession2 = mock(WebSocketSession.class);

        when(mockSession1.isOpen()).thenReturn(true);
        when(mockSession2.isOpen()).thenReturn(true);

        player.addSession(mockSession1);
        player.addSession(mockSession2);

        // Act
        player.clearSessions();

        // Assert
        verify(mockSession1, times(1)).close();
        verify(mockSession2, times(1)).close();
    }

    @Test
    void testClearSessionsWithIOException() throws IOException {
        WebSocketSession mockSession = mock(WebSocketSession.class);

        player.addSession(mockSession);

        doThrow(new IOException("Session close error")).when(mockSession).close();

        // Verify that RuntimeException is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, player::clearSessions);
        assertEquals("java.io.IOException: Session close error", exception.getMessage());
    }

    @Test
    void testSendMessageToOpenSessions() throws IOException {
        WebSocketSession mockSession1 = mock(WebSocketSession.class);
        WebSocketSession mockSession2 = mock(WebSocketSession.class);

        when(mockSession1.isOpen()).thenReturn(true);
        when(mockSession2.isOpen()).thenReturn(false);

        player.addSession(mockSession1);
        player.addSession(mockSession2);

        // Act
        player.sendMessage("Hello");

        // Assert
        verify(mockSession1, times(1)).sendMessage(new TextMessage("Hello"));
        verify(mockSession2, never()).sendMessage(any(TextMessage.class));
    }

    @Test
    void testSendMessageToEmptySessions() {
        // Act
        player.sendMessage("Hello");

        // Assert
        // No exceptions should be thrown and no sessions to send to
    }

    @Test
    void testSendMessageWithIOException() throws IOException {
        WebSocketSession mockSession = mock(WebSocketSession.class);
        when(mockSession.isOpen()).thenReturn(true);

        player.addSession(mockSession);

        doThrow(new IOException("Send message error")).when(mockSession).sendMessage(any(TextMessage.class));
    }

    @Test
    void testEqualsAndHashCode() {
        Player samePlayer = new Player(playerCredentials);
        Player differentPlayer = new Player(mock(PlayerCredentials.class));

        // Act & Assert
        assertEquals(player, samePlayer, "Players with the same credentials should be equal");
        assertNotEquals(player, differentPlayer, "Players with different credentials should not be equal");
        assertEquals(player.hashCode(), samePlayer.hashCode(), "HashCodes for equal players should match");
    }

    @Test
    void testToString() {
        assertEquals("Player{testUser}", player.toString(), "toString should return the correct representation");
    }

    @Test
    void testNotEqualsWithDifferentObjectType() {
        assertNotEquals(player, new Object(), "Player should not be equal to an object of a different type");
    }

    @Test
    void testNotEqualsWithNull() {
        assertNotEquals(player, null, "Player should not be equal to null");
    }
}
