package me.kuropatva.thatsall.model.events;

import me.kuropatva.thatsall.model.game.Game;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.function.BiFunction;

public class EventRegister {

    private final EnumMap<EventType, LinkedList<BiFunction<Game, Event, Boolean>>> map = new EnumMap<>(EventType.class);

    public Event trigger(EventType eventType, Game game, Event event) {
        var listeners = map.get(eventType);
        if (listeners == null) return event;
        ArrayList<BiFunction<Game, Event, Boolean>> removeList = new ArrayList<>();
        listeners.forEach(trigger -> {
            var result = trigger.apply(game, event);
            if (result) removeList.add(trigger);
        });
        removeList.forEach(listeners::remove);
        return event;
    }

    public void register(EventListener listener, EventType... eventType) {
        for (EventType type : eventType) {
            var event = map.getOrDefault(type, null);
            if (event == null) {
                map.put(type, event = new LinkedList<>());
            }
            event.add(listener::trigger);
        }
    }
}
