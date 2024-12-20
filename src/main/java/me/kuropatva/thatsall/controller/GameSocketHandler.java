package me.kuropatva.thatsall.controller;

import me.kuropatva.thatsall.model.lobby.Lobby;
import me.kuropatva.thatsall.model.player.Player;

public class GameSocketHandler {

    private Lobby lobby;

    public GameSocketHandler(Lobby lobby) {
        this.lobby = lobby;
    }

    public void handle(Player player, String message) {
        var cmd = new CommandArgs(lobby, player, message);
        switch (cmd.getCommandName()) {
            case "UPT" -> new RefreshCommand().run(cmd);
            case "PLP" -> new PlayPowerCommand().run(cmd);
            case "PLV" -> new PlayValueCommand().run(cmd);
        }
    }
}
