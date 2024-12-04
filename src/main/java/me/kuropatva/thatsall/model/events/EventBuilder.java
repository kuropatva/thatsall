package me.kuropatva.thatsall.model.events;

import me.kuropatva.thatsall.model.events.values.EventInt;
import me.kuropatva.thatsall.model.events.values.EventString;
import me.kuropatva.thatsall.model.events.values.EventValueType;
import me.kuropatva.thatsall.model.game.Game;
import me.kuropatva.thatsall.model.player.Player;

import java.util.HashMap;

public class EventBuilder {

    private final HashMap<String, EventValueType<?>> values = new HashMap<>();
    private Player player = null;
    private Game game = null;

    private EventBuilder() {}

    public static EventBuilder get() {
        return new EventBuilder();
    }

    public EventBuilder player(Player player) {
        this.player = player;
        return this;
    }

    public EventBuilder game(Game game) {
        this.game = game;
        return this;
    }

    public EventBuilder value(String name, EventValueType<?> value) {
        if (name == null) throw new NullPointerException();
        values.put(name, value);
        return this;
    }

    public Event build() {
        return new Event(this);
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public HashMap<String, EventValueType<?>> getValues() {
        return values;
    }
}
