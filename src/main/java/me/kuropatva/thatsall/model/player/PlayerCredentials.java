package me.kuropatva.thatsall.model.player;

import java.util.Objects;

public class PlayerCredentials {
    private final String username;
    private final String passcode;


    public PlayerCredentials(String username, String passcode) {
        this.username = username;
        this.passcode = passcode;
    }

    public boolean authorize(String passcode) {
        return Objects.equals(this.passcode, passcode);
    }

    public String username() {
        return username;
    }
}
