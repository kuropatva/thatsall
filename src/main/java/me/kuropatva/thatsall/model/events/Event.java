package me.kuropatva.thatsall.model.events;

import me.kuropatva.thatsall.model.events.values.EventValueType;
import me.kuropatva.thatsall.model.game.Game;
import me.kuropatva.thatsall.model.player.Player;

import java.util.HashMap;

public class Event {

    private final Player executor;
    private final Game game;
    private final HashMap<String, EventValueType<?>> values;

    public Event(EventBuilder eventBuilder) {
        this.executor = eventBuilder.getPlayer();
        this.game = eventBuilder.getGame();
        this.values = eventBuilder.getValues();
    }


    public EventValueType<?> getValue(String key) {
        return values.getOrDefault(key, null);
    }

    public void setValue(String key, EventValueType<?> value) {
        values.put(key, value);
    }

    public Player getPlayer() {
        return executor;
    }

    public Game getGame() {
        return game;
    }
}