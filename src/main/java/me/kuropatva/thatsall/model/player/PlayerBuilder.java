package me.kuropatva.thatsall.model.player;

import me.kuropatva.thatsall.model.lobby.Lobby;

public class PlayerBuilder {

    private final String username;
    private final String passcode;
    private Lobby lobby;

    private PlayerBuilder(String username, String passcode) {
        this.username = username;
        this.passcode = passcode;
    }

    public static PlayerBuilder get(String username, String passcode) {
        return new PlayerBuilder(username, passcode);
    }

    public PlayerBuilder game(Lobby lobby) {
        this.lobby = lobby;
        return this;
    }

    public Player build() {
        Player p = new Player(new PlayerCredentials(username, passcode));
        p.setLobby(lobby);
        return p;
    }
}
