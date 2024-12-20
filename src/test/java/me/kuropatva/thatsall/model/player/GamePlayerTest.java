package me.kuropatva.thatsall.model.player;

import me.kuropatva.thatsall.model.cards.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GamePlayerTest {

    private GamePlayer gamePlayer;

    @BeforeEach
    void setUp() {
        gamePlayer = new GamePlayer();
    }

    @Test
    void testInitialValues() {
        assertEquals(0, gamePlayer.getPoints(), "Initial points should be 0");
        assertEquals(0, gamePlayer.getGold(), "Initial gold should be 0");
        assertFalse(gamePlayer.isReady(), "Initial ready state should be false");
        assertEquals(-1, gamePlayer.getPlayedValueCard(), "Initial played value card should be -1");
        assertTrue(gamePlayer.getPlayedPowerCards().isEmpty(), "Initial played power cards should be empty");
    }

    @Test
    void testAddPoints() {
        gamePlayer.addPoints(10);
        assertEquals(10, gamePlayer.getPoints(), "Points should be incremented correctly");

        gamePlayer.addPoints(5);
        assertEquals(15, gamePlayer.getPoints(), "Points should accumulate correctly");

        gamePlayer.addPoints(-5);
        assertEquals(10, gamePlayer.getPoints(), "Negative points should decrease the total");
    }

    @Test
    void testSetPoints() {
        gamePlayer.setPoints(50);
        assertEquals(50, gamePlayer.getPoints(), "Points should be set correctly");

        gamePlayer.setPoints(0);
        assertEquals(0, gamePlayer.getPoints(), "Points should reset correctly");
    }

    @Test
    void testAddGold() {
        gamePlayer.addGold(20);
        assertEquals(20, gamePlayer.getGold(), "Gold should be incremented correctly");

        gamePlayer.addGold(10);
        assertEquals(30, gamePlayer.getGold(), "Gold should accumulate correctly");

        gamePlayer.addGold(-5);
        assertEquals(25, gamePlayer.getGold(), "Negative gold should decrease the total");
    }

    @Test
    void testSetGold() {
        gamePlayer.setGold(100);
        assertEquals(100, gamePlayer.getGold(), "Gold should be set correctly");

        gamePlayer.setGold(0);
        assertEquals(0, gamePlayer.getGold(), "Gold should reset correctly");
    }

    @Test
    void testSetReady() {
        gamePlayer.setReady(true);
        assertTrue(gamePlayer.isReady(), "Ready state should be set to true");

        gamePlayer.setReady(false);
        assertFalse(gamePlayer.isReady(), "Ready state should be set to false");
    }

    @Test
    void testSetPlayedValueCard() {
        gamePlayer.setPlayedValueCard(5);
        assertEquals(5, gamePlayer.getPlayedValueCard(), "Played value card should be set correctly");

        gamePlayer.setPlayedValueCard(-1);
        assertEquals(-1, gamePlayer.getPlayedValueCard(), "Played value card should reset correctly");
    }

    @Test
    void testAddPlayerPowerCard() {
        Card mockCard1 = mock(Card.class);
        Card mockCard2 = mock(Card.class);

        gamePlayer.addPlayerPowerCard(mockCard1);
        gamePlayer.addPlayerPowerCard(mockCard2);

        LinkedList<Card> playedCards = gamePlayer.getPlayedPowerCards();
        assertEquals(2, playedCards.size(), "Played power cards should have two cards");
        assertTrue(playedCards.contains(mockCard1), "Played power cards should contain the first card");
        assertTrue(playedCards.contains(mockCard2), "Played power cards should contain the second card");
    }

    @Test
    void testPlayerHand() {
        GamePlayerHand playerHand = gamePlayer.playerHand();
        assertNotNull(playerHand, "Player hand should not be null");
    }
}
