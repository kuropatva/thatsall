package me.kuropatva.thatsall.player;

import me.kuropatva.thatsall.model.lobby.Lobby;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class Player {
    private final PlayerCredentials playerCredentials;
    private final GamePlayer gamePlayer = new GamePlayer();
    private Lobby lobby;
    private LinkedList<WebSocketSession> sessions = new LinkedList<>();

    public Player(PlayerCredentials playerCredentials) {
        this.playerCredentials = playerCredentials;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void getLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public String username() {
        return playerCredentials.username();
    }

    public boolean authorize(String passcode) {
        return playerCredentials.authorize(passcode);
    }

    public void addSession(WebSocketSession session) {
        this.sessions.add(session);
    }

    public void removeSession(WebSocketSession session) {
        this.sessions.remove(session);
    }

    public GamePlayer gamePlayer() {
        return gamePlayer;
    }

    public void clearSessions() {
        for (var s : sessions) {
            try {
                s.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        sessions.clear();
    }

    public void sendMessage(String message) {
        if (sessions.isEmpty()) return;
        try {
            for (var s : sessions) {
                if (s.isOpen()) {
                    s.sendMessage(new TextMessage(message));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(playerCredentials, player.playerCredentials);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(playerCredentials);
    }

    @Override
    public String toString() {
        return "Player{" + playerCredentials.username() + '}';
    }
}
