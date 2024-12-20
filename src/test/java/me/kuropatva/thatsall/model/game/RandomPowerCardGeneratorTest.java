package me.kuropatva.thatsall.model.game;

import me.kuropatva.thatsall.model.cards.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomPowerCardGeneratorTest {

    @Test
    void get() {
        var rng = new RandomPowerCardGenerator();
        for (int i = 0; i < 10; i++) {
            assertTrue(rng.get() instanceof Card);
        }
    }
}