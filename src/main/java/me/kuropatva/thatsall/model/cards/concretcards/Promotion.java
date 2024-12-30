package me.kuropatva.thatsall.model.cards.concretcards;

import me.kuropatva.thatsall.model.cards.Card;
import me.kuropatva.thatsall.model.events.Event;
import me.kuropatva.thatsall.model.game.Game;

public class Promotion extends Card {
    @Override
    public boolean trigger(Game game, Event event) {
        var i = getOwner().gamePlayer().getPlayedValueCard();
        getOwner().gamePlayer().modifyPlayedValueCard(i + 2);
        return true;
    }
}
