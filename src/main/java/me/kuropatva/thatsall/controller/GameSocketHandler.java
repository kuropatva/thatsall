package me.kuropatva.thatsall.controller;

import me.kuropatva.thatsall.controller.commands.PlayPowerCommand;
import me.kuropatva.thatsall.controller.commands.PlayValueCommand;
import me.kuropatva.thatsall.controller.commands.RefreshCommand;
import me.kuropatva.thatsall.model.lobby.Lobby;
import me.kuropatva.thatsall.model.player.Player;

public class GameSocketHandler {

    private final Lobby lobby;

    public GameSocketHandler(Lobby lobby) {
        this.lobby = lobby;
    }

    public void handleIncoming(Player player, String message) {
        var cmd = new CommandArgs(lobby, player, message);
        switch (cmd.getCommandName()) {
            case "UPT" -> new RefreshCommand().run(cmd);
            case "PLP" -> new PlayPowerCommand().run(cmd);
            case "PLV" -> new PlayValueCommand().run(cmd);
        }
    }

    public void refresh(Player player) {

    }
}
