package me.kuropatva.thatsall.model.cards.concretcards;

import me.kuropatva.thatsall.model.cards.Card;
import me.kuropatva.thatsall.model.events.Event;
import me.kuropatva.thatsall.model.game.Game;

public class Mystery extends Card {
    @Override
    public boolean trigger(Game game, Event event) {
        getOwner().gamePlayer().addPlayedPowerCard(game.takePowerCard());
        return true;
    }
}
