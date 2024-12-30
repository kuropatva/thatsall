package me.kuropatva.thatsall.view;

import me.kuropatva.thatsall.model.lobby.Lobby;
import me.kuropatva.thatsall.model.player.Player;

public class PlayerGameView {

    private final Lobby lobby;

    public PlayerGameView(Lobby lobby) {
        this.lobby = lobby;
    }

    public String getJson(Player player) {
        return "{" +
                simpleJsonEntry("gold", String.valueOf(player.gamePlayer().getGold())) +
                simpleJsonEntry("name", player.username()) +
                player.gamePlayer().playerHand().toJson() + ", " +
                "\"players\": " + lobby.toJson() +
                "}";
    }

    private String simpleJsonEntry(String a, String b) {
        return "\"" + a + "\": \"" + b + "\", ";
    }

    private String simpleJsonEntryNoComma(String a, String b) {
        return "\"" + a + "\": \"" + b + "\"";
    }
}
