package me.kuropatva.thatsall.model.cards.concretcards;

import me.kuropatva.thatsall.model.cards.Card;
import me.kuropatva.thatsall.model.events.Event;
import me.kuropatva.thatsall.model.game.Game;

public class Sevens extends Card {
    @Override
    public boolean trigger(Game game, Event event) {
        if (event.getPlayer() == null || event.getPlayer().gamePlayer().getPlayedValueCard() != 7) return false;
        event.getPlayer().gamePlayer().addGold(17);
        for (int i = 0; i < 3; i++) {
            event.getPlayer().gamePlayer().playerHand().add(7);
        }
        return true;
    }
}
