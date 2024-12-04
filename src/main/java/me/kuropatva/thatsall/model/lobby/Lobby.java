package me.kuropatva.thatsall.model.lobby;

import me.kuropatva.thatsall.controller.GameSocketHandler;
import me.kuropatva.thatsall.model.game.Game;
import me.kuropatva.thatsall.model.player.Player;

import java.util.HashMap;
import java.util.Iterator;

public class Lobby {
    private final HashMap<String, Player> players = new HashMap<>();
    private String password = null;
    private GameSocketHandler gameSocketHandler = new GameSocketHandler(this);
    private Game game = new Game(this);

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean authorize(String password) {
        return (this.password == null || this.password.equals(password));
    }

    public boolean hasPlayerName(String username) {
        if (username == null || username.isBlank()) return false;
        return players.containsKey(username);
    }

    public int playerSize() {
        return players.size();
    }

    public Game game() {
        return game;
    }

    public Player getPlayer(String username) {
        return players.get(username);
    }

    public void addPlayer(Player player) {
        players.put(player.username(), player);
    }

    public Iterable<Player> players() {
        return players.values();
    }

    public GameSocketHandler getGameSocketHandler() {
        return gameSocketHandler;
    }

    public void close() {
        players.forEach((ignored, p) -> {
            p.getLobby(null);
            p.clearSessions();
        });
        players.clear();
    }
}
