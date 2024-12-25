package me.kuropatva.thatsall.model.events;

import me.kuropatva.thatsall.model.events.values.EventValueType;
import me.kuropatva.thatsall.model.player.Player;

import java.util.HashMap;

public class Event {

    private final Player player;
    private final HashMap<String, EventValueType<?>> values;

    private Event(EventBuilder eventBuilder) {
        this.player = eventBuilder.player;
        this.values = eventBuilder.values;
    }

    private Event(Player player) {
        this.player = player;
        this.values = new HashMap<>();
    }


    public EventValueType<?> getValue(String key) {
        return values.getOrDefault(key, null);
    }

    public void setValue(String key, EventValueType<?> value) {
        values.put(key, value);
    }

    public Player getPlayer() {
        return player;
    }

    public static EventBuilder builder() {
        return new EventBuilder();
    }

    public static Event statelessEvent() {
        return new Event((Player) null);
    }

    public static Event statelessEvent(Player player) {
        return new Event(player);
    }

    public static class EventBuilder {

        private final HashMap<String, EventValueType<?>> values = new HashMap<>();
        private Player player = null;

        private EventBuilder() {
        }

        public EventBuilder player(Player player) {
            this.player = player;
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
