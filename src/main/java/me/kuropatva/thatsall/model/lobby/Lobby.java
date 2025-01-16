package me.kuropatva.thatsall.model.lobby;

import me.kuropatva.thatsall.controller.GameSocketHandler;
import me.kuropatva.thatsall.model.game.Game;
import me.kuropatva.thatsall.model.player.Player;
import me.kuropatva.thatsall.view.Jsonable;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Lobby implements Jsonable {
    private final HashMap<String, Player> players = new HashMap<>();
    private String password = null;
    private final GameSocketHandler gameSocketHandler = new GameSocketHandler(this);
    private final Game game = new Game(this);
    private volatile LocalDateTime lastUpdate = LocalDateTime.now();

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
        if (game.state() != Game.State.LOBBY) return;
        updateInactive();
        players.put(player.username(), player);
    }

    public Iterable<Player> players() {
        return players.values();
    }

    public GameSocketHandler getGameSocketHandler() {
        return gameSocketHandler;
    }

    public void updateInactive() {
        lastUpdate = LocalDateTime.now();
    }

    public LocalDateTime getInactiveTime() {
        return lastUpdate;
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("[");
        players.values().forEach(p -> sb.append("{\"name\": \"").append(p.username()).append("\", \"points\": ").append(p.gamePlayer().getPoints()).append("}, "));
        int r = sb.lastIndexOf(",");
        if (r != -1)
            sb.replace(r, r + 2, "]");
        return sb.toString();
    }

    public synchronized void close() {
        players.forEach((ignored, p) -> {
            p.sendMessage("Lobby has been closed.");
            p.setLobby(null);
            p.clearSessions();
        });
        players.clear();
    }
}
