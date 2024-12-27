package me.kuropatva.thatsall.web.socket;

import me.kuropatva.thatsall.model.lobby.LobbyManager;
import me.kuropatva.thatsall.model.player.Player;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;

public class WebSocketHandler extends TextWebSocketHandler {

    private static final HashMap<WebSocketSession, Player> sessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        var username = (String) session.getAttributes().get("username");
        var game = (String) session.getAttributes().get("gameID");
        var player = LobbyManager.game(game).getPlayer(username);
        sessions.putIfAbsent(session, player);
        player.addSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        var player = sessions.remove(session);
        player.removeSession(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        var player = sessions.get(session);
        player.getLobby().getGameSocketHandler().handleIncoming(player, message.getPayload());
    }
}