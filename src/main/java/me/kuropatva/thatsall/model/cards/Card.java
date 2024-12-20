package me.kuropatva.thatsall.model.cards;

import me.kuropatva.thatsall.model.events.Event;
import me.kuropatva.thatsall.model.events.EventListener;
import me.kuropatva.thatsall.model.player.Player;

public abstract class Card implements EventListener {

    private Player player;

    public abstract int getID();

    @Override
    public boolean trigger(Event event) {
        return false;
    }

    public Player getOwner() {
        return player;
    }

    public void setOwner(Player player) {
        this.player = player;
    }
}
