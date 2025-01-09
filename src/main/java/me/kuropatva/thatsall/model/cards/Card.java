package me.kuropatva.thatsall.model.cards;

import me.kuropatva.thatsall.model.events.Event;
import me.kuropatva.thatsall.model.events.EventListener;
import me.kuropatva.thatsall.model.events.EventType;
import me.kuropatva.thatsall.model.game.Game;
import me.kuropatva.thatsall.model.player.Player;

import java.util.Arrays;

public abstract class Card implements EventListener {

    private Player player;

    public String getID() {
        return this.getClass().getSimpleName();
    }

    public EventType[] getEvents() {
        return CardDataTable.get(getID()).events();
    }

    public int getCost() {
        return CardDataTable.get(getID()).cost();
    }

    public String getName() {
        return CardDataTable.get(getID()).name();
    }

    public String getDescription() {
        return CardDataTable.get(getID()).description();
    }


    @Override
    public abstract boolean trigger(Game game, Event event);

    public Player getOwner() {
        return player;
    }

    public void setOwner(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "\"" + getID() + "\"";
    }

    public String toFancyString() {
        return "Card{" +
                "player=" + player + "," +
                "id=" + getID() + "," +
                "cost=" + getCost() + "," +
                "name=" + getName() + "," +
                "desc=" + getDescription() + "," +
                "events=" + Arrays.toString(getEvents()) +
                '}';
    }
}
