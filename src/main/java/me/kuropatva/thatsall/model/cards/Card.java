package me.kuropatva.thatsall.model.cards;

import me.kuropatva.thatsall.model.events.Event;
import me.kuropatva.thatsall.model.events.EventListener;

public abstract class Card implements EventListener {

    public abstract int getID();

    @Override
    public boolean trigger(Event event) {
        return false;
    }
}
