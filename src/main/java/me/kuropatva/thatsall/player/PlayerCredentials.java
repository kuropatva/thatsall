package me.kuropatva.thatsall.player;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerCredentials that = (PlayerCredentials) o;
        return Objects.equals(username, that.username) && Objects.equals(passcode, that.passcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, passcode);
    }
}
