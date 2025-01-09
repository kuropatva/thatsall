package me.kuropatva.thatsall.model.cards.concretcards;

import me.kuropatva.thatsall.model.cards.Card;
import me.kuropatva.thatsall.model.events.Event;
import me.kuropatva.thatsall.model.game.Game;

public class Reset extends Card {
    @Override
    public boolean trigger(Game game, Event event) {
        getOwner().gamePlayer().setGold(5);
        var playerHand = getOwner().gamePlayer().playerHand();
        playerHand.clearAll();
        for (int i = 0; i < 5; i++) {
            playerHand.add(game.takePowerCard());
            playerHand.add(game.takeValueCard());
        }
        return true;
    }
}
