package me.kuropatva.thatsall.controller;

import me.kuropatva.thatsall.controller.commands.PlayPowerCommand;
import me.kuropatva.thatsall.controller.commands.PlayValueCommand;
import me.kuropatva.thatsall.controller.commands.StartGameCommand;
import me.kuropatva.thatsall.model.lobby.Lobby;
import me.kuropatva.thatsall.model.player.Player;

public class GameSocketHandler {

    private final Lobby lobby;

    public GameSocketHandler(Lobby lobby) {
        this.lobby = lobby;
    }

    public static String stateHash(Player player, Lobby lobby) {
        return player.gamePlayer().hashCode() + "" + lobby.game().hashCode();
    }

    public void handleIncoming(Player player, String message) {
        var cmd = new CommandArgs(lobby, player, message);
        switch (cmd.getCommandName()) {
            case "UPT" -> refreshPlayer(player);
            case "PLP" -> new PlayPowerCommand().run(cmd);
            case "PLV" -> new PlayValueCommand().run(cmd);
            case "STR" -> new StartGameCommand().run(cmd);
        }
    }

    public void refreshPlayer(Player player) {
        var json = ""; // TODO
        player.sendMessage("REF " + stateHash(player, lobby) + " " + json);
    }

    public void finishGame(Player player, String winner) {
        player.sendMessage("FNG " + winner);
    }

    public void error(Player player, String message) {
        player.sendMessage("ERR " + message);
    }

    private void message(Player player, String message) {
        player.sendMessage("MSG " + message);
    }

    public void finishRound(Player player, Player winner) {
        player.sendMessage(""); // TODO
        refreshPlayer(player);
    }

}
