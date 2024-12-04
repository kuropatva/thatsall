package me.kuropatva.thatsall.model.events;

public interface EventListener {
    boolean trigger(Event event);
}
