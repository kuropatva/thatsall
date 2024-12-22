package me.kuropatva.thatsall.model.events;

import me.kuropatva.thatsall.model.events.values.EventValueType;
import me.kuropatva.thatsall.model.game.Game;
import me.kuropatva.thatsall.model.player.Player;

import java.util.HashMap;

public class Event {

    private final Player executor;
    private final Game game;
    private final HashMap<String, EventValueType<?>> values;

    private Event(EventBuilder eventBuilder) {
        this.executor = eventBuilder.player;
        this.game = eventBuilder.game;
        this.values = eventBuilder.values;
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

    public static EventBuilder builder() {
        return new EventBuilder();
    }

    public static class EventBuilder {

        private final HashMap<String, EventValueType<?>> values = new HashMap<>();
        private Player player = null;
        private Game game = null;

        private EventBuilder() {
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
    }

}
