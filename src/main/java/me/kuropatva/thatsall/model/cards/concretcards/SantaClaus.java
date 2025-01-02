package me.kuropatva.thatsall.model.cards.concretcards;

import me.kuropatva.thatsall.model.cards.Card;
import me.kuropatva.thatsall.model.events.Event;
import me.kuropatva.thatsall.model.game.Game;

public class SantaClaus extends Card {
    @Override
    public boolean trigger(Game game, Event event) {
        game.lobby().players().forEach(p -> p.gamePlayer().addPoints(2));
        return true;
    }
}
