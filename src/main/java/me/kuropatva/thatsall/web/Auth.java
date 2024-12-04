package me.kuropatva.thatsall.web;

import me.kuropatva.thatsall.model.lobby.Lobby;
import me.kuropatva.thatsall.model.lobby.LobbyManager;

public class Auth {

    public static boolean authorize(String gameID, String username, String passcode) {
        var game = LobbyManager.game(gameID);
        return authorize(game, username, passcode);
    }

    public static boolean authorize(Lobby lobby, String username, String passcode) {
        return (lobby != null && lobby.hasPlayerName(username) && lobby.getPlayer(username).authorize(passcode));
    }
}
