package me.kuropatva.thatsall.model.events.values;

import me.kuropatva.thatsall.model.player.Player;

public class EventPlayer extends EventValueType<Player> {

    private Player player;

    public EventPlayer(Player player) {
        this.player = player;
    }

    @Override
    public Player get() {
        return player;
    }

    @Override
    public void store(Player player) {
        this.player = player;
    }
}
