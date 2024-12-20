package me.kuropatva.thatsall.model.cards.concretcards;

import me.kuropatva.thatsall.model.cards.Card;
import me.kuropatva.thatsall.model.events.Event;

public class TestCard extends Card {
    @Override
    public int getID() {
        return 0;
    }

    @Override
    public boolean trigger(Event event) {
        return false;
    }
}
