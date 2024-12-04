package me.kuropatva.thatsall.model.events;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.function.Function;

public class EventRegister {

    private final EnumMap<EventType, LinkedList<Function<Event, Boolean>>> map = new EnumMap<>(EventType.class);

    public Event trigger(EventType eventType, Event event) {
        var listeners = map.get(eventType);
        if (listeners == null) return event;
        listeners.forEach(trigger -> {
            var result = trigger.apply(event);
            if (result) listeners.remove(trigger);
        });
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
