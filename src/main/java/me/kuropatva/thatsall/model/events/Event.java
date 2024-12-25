package me.kuropatva.thatsall.model.events;

import me.kuropatva.thatsall.model.events.values.EventValueType;
import me.kuropatva.thatsall.model.player.Player;

import java.util.HashMap;

public class Event {

    private final Player player;
    private final HashMap<String, EventValueType<?>> values;

    private final static Event statelessEvent = new Event((Player) null);

    private Event(EventBuilder eventBuilder) {
        this.player = eventBuilder.player;
        this.values = eventBuilder.values;
    }

    private Event(Player player) {
        this.player = player;
        this.values = null;
    }

    public static Event statelessEvent() {
        return Event.statelessEvent;
    }

    public EventValueType<?> getValue(String key) {
        if (values == null) return null;
        return values.getOrDefault(key, null);
    }

    public Player getPlayer() {
        return player;
    }

    public static EventBuilder builder() {
        return new EventBuilder();
    }

    public void setValue(String key, EventValueType<?> value) {
        if (value == null) return;
        values.put(key, value);
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
