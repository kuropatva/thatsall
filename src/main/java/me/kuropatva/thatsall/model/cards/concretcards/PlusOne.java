package me.kuropatva.thatsall.model.cards.concretcards;

import me.kuropatva.thatsall.model.cards.Card;
import me.kuropatva.thatsall.model.events.Event;
import me.kuropatva.thatsall.model.events.values.EventInt;
import me.kuropatva.thatsall.model.game.Game;

public class PlusOne extends Card {
    @Override
    public boolean trigger(Game game, Event event) {
        EventInt value = (EventInt) event.getValue("INT_POINTS");
        value.store(value.get() + 1);
        return true;
    }
}
