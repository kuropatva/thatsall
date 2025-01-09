package me.kuropatva.thatsall.model.lobby;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LobbyManager {

    private static final Map<String, Lobby> gameMap = Collections.synchronizedMap(new HashMap<>());

    public static Lobby newGame(String gameID) {
        if (gameID == null) return  null;
        Lobby lobby = new Lobby();
        gameMap.put(gameID, lobby);
        return lobby;
    }

    public static Lobby game(String gameID) {
        return gameMap.get(gameID);
    }

    public static void close(Lobby lobby) {
        if (lobby == null) return;
        lobby.close();
        gameMap.entrySet().removeIf(entry -> entry.getValue().equals(lobby));
    }

    public static void close(String lobby) {
        var game = game(lobby);
        if (game == null) return;
        game.close();
        gameMap.remove(lobby);
    }

    public static Map<String, Lobby> getGameMap() {
        return gameMap;
    }
}
