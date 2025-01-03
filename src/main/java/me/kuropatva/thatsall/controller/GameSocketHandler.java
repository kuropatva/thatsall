package me.kuropatva.thatsall.controller;

import me.kuropatva.thatsall.controller.commands.PlayPowerCommand;
import me.kuropatva.thatsall.controller.commands.PlayValueCommand;
import me.kuropatva.thatsall.controller.commands.StartGameCommand;
import me.kuropatva.thatsall.model.lobby.Lobby;
import me.kuropatva.thatsall.model.player.Player;
import me.kuropatva.thatsall.view.PlayerGameView;

public class GameSocketHandler {

    private final Lobby lobby;
    private final PlayerGameView gameView;

    public GameSocketHandler(Lobby lobby) {
        this.lobby = lobby;
        this.gameView = new PlayerGameView(lobby);
    }

    public static String stateHash(Player player, Lobby lobby) {
        return player.gamePlayer().hashCode() + "" + lobby.game().hashCode();
    }

    public void handleIncoming(Player player, String message) {
        System.out.println(player.username() + ": " + message);
        var cmd = new CommandArgs(lobby, player, message);
        switch (cmd.getCommandName()) {
            case "UPT" -> refreshPlayer(player);
            case "PLP" -> new PlayPowerCommand().run(cmd);
            case "PLV" -> new PlayValueCommand().run(cmd);
            case "STR" -> new StartGameCommand().run(cmd);
        }
    }

    public void refreshPlayer(Player player) {
        var json = gameView.getJson(player);
        player.sendMessage("REF " + stateHash(player, lobby) + " " + json);
    }

    public void finishGame(Player player, String winner) {
        player.sendMessage("FNG " + winner);
    }

    public void error(Player player, String message) {
        player.sendMessage("ERR " + message);
    }

    public void message(Player player, String message) {
        player.sendMessage("MSG " + message);
    }

    public void finishRound(Player player, Player winner) {
        player.sendMessage("FNR " + (winner != null ? winner.username() : ""));
        refreshPlayer(player);
    }

}
