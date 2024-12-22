package me.kuropatva.thatsall.model.game;

import me.kuropatva.thatsall.model.cards.Card;
import me.kuropatva.thatsall.model.cards.RandomPowerCardGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomPowerCardGeneratorTest {

    @Test
    void get() {
        var rng = new RandomPowerCardGenerator();
        for (int i = 0; i < 10; i++) {
            assertInstanceOf(Card.class, rng.get());
        }
    }
}