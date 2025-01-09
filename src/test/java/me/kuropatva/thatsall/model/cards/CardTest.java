package me.kuropatva.thatsall.model.cards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CardTest {

    //
    @Test
    public void verify() {
        assertDoesNotThrow(RandomPowerCardGenerator::verify);
    }
}