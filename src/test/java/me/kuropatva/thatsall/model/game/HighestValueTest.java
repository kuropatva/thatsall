package me.kuropatva.thatsall.model.game;

import me.kuropatva.thatsall.model.player.Player;
import me.kuropatva.thatsall.model.player.PlayerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class HighestValueTest {

    private HighestValue highestValue;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    public void setUp() {
        highestValue = new HighestValue();
        player1 = PlayerFactory.get("Player1", "a").build();
        player2 = PlayerFactory.get("Player2", "a").build();
        player3 = PlayerFactory.get("Player3", "a").build();
    }

    @Test
    public void testAddSingleValue() {
        highestValue.add(10, player1);

        assertEquals(10, highestValue.getValue());
        assertEquals(player1, highestValue.getPlayer());
    }

    @Test
    public void testAddHigherValue() {
        highestValue.add(10, player1);
        highestValue.add(20, player2);

        assertEquals(20, highestValue.getValue());
        assertEquals(player2, highestValue.getPlayer());
    }

    @Test
    public void testAddLowerValue() {
        highestValue.add(20, player1);
        highestValue.add(10, player2);

        assertEquals(20, highestValue.getValue());
        assertEquals(player1, highestValue.getPlayer());
    }

    @Test
    public void testAddEqualValue() {
        highestValue.add(10, player1);
        highestValue.add(10, player2);

        assertEquals(10, highestValue.getValue());
        assertNull(highestValue.getPlayer());
    }

    @Test
    public void testAddMultipleValuesWithEqualAndHigher() {
        highestValue.add(10, player1);
        highestValue.add(10, player2);
        highestValue.add(15, player3);

        assertEquals(15, highestValue.getValue());
        assertEquals(player3, highestValue.getPlayer());
    }

    @Test
    public void testInitialState() {
        assertEquals(0, highestValue.getValue());
        assertNull(highestValue.getPlayer());
    }
}