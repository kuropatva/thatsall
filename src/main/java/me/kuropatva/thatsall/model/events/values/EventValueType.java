package me.kuropatva.thatsall.model.events.values;

public abstract class EventValueType<T> {

    public abstract T get();
    public abstract void store(T t);
}
