package me.kuropatva.thatsall.model.cards;

import me.kuropatva.thatsall.model.cards.concretcards.AnotherCard;
import me.kuropatva.thatsall.model.cards.concretcards.TestCard;
import me.kuropatva.thatsall.model.events.EventRegister;
import org.junit.jupiter.api.Test;

class CardDataTableTest {

    @Test
    void get() {
        var register = new EventRegister();
        var card = new AnotherCard();
        var card2 = new TestCard();
        System.out.println(card);
        System.out.println(card2);
    }
}