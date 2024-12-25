package me.kuropatva.thatsall.model.cards.concretcards;

import me.kuropatva.thatsall.model.cards.Card;
import me.kuropatva.thatsall.model.events.Event;
import me.kuropatva.thatsall.model.game.Game;

public class TestCard extends Card {
    @Override
    public int getID() {
        return 0;
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public boolean trigger(Game game, Event event) {
        return false;
    }
}
