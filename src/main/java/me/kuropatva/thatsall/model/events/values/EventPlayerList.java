package me.kuropatva.thatsall.model.events.values;

import me.kuropatva.thatsall.model.player.Player;

import java.util.ArrayList;

public class EventPlayerList extends EventValueType<ArrayList<Player>> {

    private ArrayList<Player> players;

    public EventPlayerList(ArrayList<Player> players) {
        this.players = players;
    }

    @Override
    public ArrayList<Player> get() {
        return players;
    }

    @Override
    public void store(ArrayList<Player> players) {
        this.players = players;
    }
}
