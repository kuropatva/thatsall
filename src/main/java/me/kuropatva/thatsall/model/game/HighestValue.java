package me.kuropatva.thatsall.model.game;

import me.kuropatva.thatsall.model.player.Player;

public class HighestValue {

    private int highestValue = 0;
    private Player holder = null;


    public void add(int value, Player player) {
        if (highestValue < value) {
            highestValue = value;
            holder = player;
        } else if (highestValue == value) {
            holder = null;
        }
    }

    public Player getPlayer() {
        return holder;
    }

    public int getValue() {
        return highestValue;
    }
}
