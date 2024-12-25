package me.kuropatva.thatsall.model.cards;

import me.kuropatva.thatsall.model.events.Event;
import me.kuropatva.thatsall.model.events.EventListener;
import me.kuropatva.thatsall.model.game.Game;
import me.kuropatva.thatsall.model.player.Player;

public abstract class Card implements EventListener {

    private Player player;

    public abstract int getID();

    public abstract int getCost();

    @Override
    public abstract boolean trigger(Game game, Event event);

    public Player getOwner() {
        return player;
    }

    public void setOwner(Player player) {
        this.player = player;
    }
}
