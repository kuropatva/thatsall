package me.kuropatva.thatsall.model.lobby;

import java.util.HashMap;

public class LobbyManager {

    private static final HashMap<String, Lobby> gameList = new HashMap<>();

    public static Lobby newGame(String gameID) {
        if (gameID == null) return  null;
        Lobby lobby = new Lobby();
        gameList.put(gameID, lobby);
        return lobby;
    }

    public static Lobby game(String gameID) {
        return gameList.get(gameID);
    }

    public static void close(Lobby lobby) {
        if (lobby == null) return;
        lobby.close();
        gameList.remove(lobby);
    }
}
