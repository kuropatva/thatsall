package me.kuropatva.thatsall.model.player;

import me.kuropatva.thatsall.model.lobby.Lobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerFactoryTest {

    private static final String TEST_USERNAME = "testUser";
    private static final String TEST_PASSCODE = "1234";
    private Lobby mockLobby;

    @BeforeEach
    void setUp() {
        mockLobby = mock(Lobby.class); // Mock the Lobby class
    }

    @Test
    void testGet() {
        // Act
        PlayerFactory factory = PlayerFactory.get(TEST_USERNAME, TEST_PASSCODE);

        // Assert
        assertNotNull(factory, "Factory instance should not be null");
        assertEquals(TEST_USERNAME, getPrivateField(factory, "username"), "Username should be set correctly");
        assertEquals(TEST_PASSCODE, getPrivateField(factory, "passcode"), "Passcode should be set correctly");
    }

    @Test
    void testGame() {
        // Arrange
        PlayerFactory factory = PlayerFactory.get(TEST_USERNAME, TEST_PASSCODE);

        // Act
        factory.game(mockLobby);

        // Assert
        assertEquals(mockLobby, getPrivateField(factory, "lobby"), "Lobby should be set correctly in the factory");
    }

    @Test
    void testBuildWithLobby() {
        // Arrange
        PlayerFactory factory = PlayerFactory.get(TEST_USERNAME, TEST_PASSCODE).game(mockLobby);

        // Act
        Player player = factory.build();

        // Assert
        assertNotNull(player, "Player instance should not be null");
        assertEquals(TEST_USERNAME, player.username(), "Player username should match the provided username");
        assertTrue(player.authorize(TEST_PASSCODE), "Player should authorize with the correct passcode");
        assertFalse(player.authorize("wrongPass"), "Player should not authorize with an incorrect passcode");
    }

    @Test
    void testBuildWithoutLobby() {
        // Arrange
        PlayerFactory factory = PlayerFactory.get(TEST_USERNAME, TEST_PASSCODE);

        // Act
        Player player = factory.build();

        // Assert
        assertNotNull(player, "Player instance should not be null");
        assertEquals(TEST_USERNAME, player.username(), "Player username should match the provided username");
        assertTrue(player.authorize(TEST_PASSCODE), "Player should authorize with the correct passcode");
        assertFalse(player.authorize("wrongPass"), "Player should not authorize with an incorrect passcode");
        // Ensure no interactions with the lobby
        verifyNoInteractions(mockLobby);
    }

    @Test
    void testMultipleInstances() {
        // Arrange
        PlayerFactory factory1 = PlayerFactory.get("User1", "Pass1").game(mockLobby);
        PlayerFactory factory2 = PlayerFactory.get("User2", "Pass2");

        // Act
        Player player1 = factory1.build();
        Player player2 = factory2.build();

        // Assert
        assertNotNull(player1, "Player1 should not be null");
        assertNotNull(player2, "Player2 should not be null");
        assertEquals("User1", player1.username(), "Player1 username should match");
        assertEquals("User2", player2.username(), "Player2 username should match");
        assertTrue(player1.authorize("Pass1"), "Player1 should authorize with the correct passcode");
        assertTrue(player2.authorize("Pass2"), "Player2 should authorize with the correct passcode");
        assertFalse(player1.authorize("Pass2"), "Player1 should not authorize with another player's passcode");
        assertFalse(player2.authorize("Pass1"), "Player2 should not authorize with another player's passcode");
    }

    @Test
    void testNullLobby() {
        // Arrange
        PlayerFactory factory = PlayerFactory.get(TEST_USERNAME, TEST_PASSCODE).game(null);

        // Act
        Player player = factory.build();

        // Assert
        assertNotNull(player, "Player instance should not be null");
        assertEquals(TEST_USERNAME, player.username(), "Player username should match the provided username");
        assertTrue(player.authorize(TEST_PASSCODE), "Player should authorize with the correct passcode");
    }

    // Helper method to access private fields for validation
    private Object getPrivateField(Object object, String fieldName) {
        try {
            var field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
