package me.kuropatva.thatsall.model.cards.concretcards;

import me.kuropatva.thatsall.model.cards.Card;
import me.kuropatva.thatsall.model.events.Event;
import me.kuropatva.thatsall.model.game.Game;

public class AllIn extends Card {
    @Override
    public boolean trigger(Game game, Event event) {
        if (event.getPlayer() != null && event.getPlayer() == getOwner()) {
            getOwner().gamePlayer().addGold(15);
            getOwner().gamePlayer().addPoints(2);
        }
        return true;
    }
}
