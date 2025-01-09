package me.kuropatva.thatsall.model.events;

import me.kuropatva.thatsall.model.game.Game;

public interface EventListener {
    boolean trigger(Game game, Event event);
}
