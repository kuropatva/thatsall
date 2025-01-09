package me.kuropatva.thatsall.model.player;

import me.kuropatva.thatsall.model.cards.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GamePlayerHandTest {

    private GamePlayerHand gamePlayerHand;

    @BeforeEach
    void setUp() {
        gamePlayerHand = new GamePlayerHand();
    }

    @Test
    void testAddPowerCard() {
        // Arrange
        Card mockCard = mock(Card.class);
        when(mockCard.getID()).thenReturn(String.valueOf(1));

        // Act
        gamePlayerHand.add(mockCard);

        // Assert
        assertTrue(gamePlayerHand.getPower(String.valueOf(1)).isPresent());
        assertEquals(mockCard, gamePlayerHand.getPower(String.valueOf(1)).get());
    }

    @Test
    void testAddValueCard() {
        // Arrange
        int valueCard = 42;

        // Act
        gamePlayerHand.add(valueCard);

        // Assert
        assertTrue(gamePlayerHand.getValue(42).isPresent());
        assertEquals(42, gamePlayerHand.getValue(42).get());
    }

    @Test
    void testRemovePowerCard() {
        // Arrange
        Card mockCard = mock(Card.class);
        when(mockCard.getID()).thenReturn(String.valueOf(1));
        gamePlayerHand.add(mockCard);

        // Act
        gamePlayerHand.remove(mockCard);

        // Assert
        assertFalse(gamePlayerHand.getPower(String.valueOf(1)).isPresent());
    }

    @Test
    void testRemoveValueCard() {
        // Arrange
        int valueCard = 42;
        gamePlayerHand.add(valueCard);

        // Act
        gamePlayerHand.remove(valueCard);

        // Assert
        assertFalse(gamePlayerHand.getValue(42).isPresent());
    }

    @Test
    void testGetPowerCardWhenNotPresent() {
        // Arrange
        Card mockCard = mock(Card.class);
        when(mockCard.getID()).thenReturn(String.valueOf(99));

        // Act
        Optional<Card> result = gamePlayerHand.getPower(String.valueOf(99));

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testGetValueCardWhenNotPresent() {
        // Act
        Optional<Integer> result = gamePlayerHand.getValue(99);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testMultiplePowerCards() {
        // Arrange
        Card mockCard1 = mock(Card.class);
        Card mockCard2 = mock(Card.class);
        when(mockCard1.getID()).thenReturn(String.valueOf(1));
        when(mockCard2.getID()).thenReturn(String.valueOf(2));

        // Act
        gamePlayerHand.add(mockCard1);
        gamePlayerHand.add(mockCard2);

        // Assert
        assertTrue(gamePlayerHand.getPower(String.valueOf(1)).isPresent());
        assertTrue(gamePlayerHand.getPower(String.valueOf(2)).isPresent());
        assertEquals(mockCard1, gamePlayerHand.getPower(String.valueOf(1)).get());
        assertEquals(mockCard2, gamePlayerHand.getPower(String.valueOf(2)).get());
    }

    @Test
    void testMultipleValueCards() {
        // Arrange
        int valueCard1 = 10;
        int valueCard2 = 20;

        // Act
        gamePlayerHand.add(valueCard1);
        gamePlayerHand.add(valueCard2);

        // Assert
        assertTrue(gamePlayerHand.getValue(10).isPresent());
        assertTrue(gamePlayerHand.getValue(20).isPresent());
        assertEquals(10, gamePlayerHand.getValue(10).get());
        assertEquals(20, gamePlayerHand.getValue(20).get());
    }

    @Test
    void testRemoveNonExistentPowerCard() {
        // Arrange
        Card mockCard = mock(Card.class);
        when(mockCard.getID()).thenReturn(String.valueOf(1));

        // Act
        gamePlayerHand.remove(mockCard);

        // Assert
        assertFalse(gamePlayerHand.getPower(String.valueOf(1)).isPresent());
    }

    @Test
    void testRemoveNonExistentValueCard() {
        // Act
        gamePlayerHand.remove(42);

        // Assert
        assertFalse(gamePlayerHand.getValue(42).isPresent());
    }
}
