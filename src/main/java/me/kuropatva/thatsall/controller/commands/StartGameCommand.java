package me.kuropatva.thatsall.controller.commands;

import me.kuropatva.thatsall.controller.CommandArgs;
import me.kuropatva.thatsall.model.game.Game;

public class StartGameCommand extends WebsocketCommand {
    @Override
    public void run(CommandArgs args) {
        if (args.lobby().game().state() != Game.State.LOBBY) {
            args.lobby().getGameSocketHandler().error(args.getPlayer(), "Game has already started.");
            return;
        }
        var success = args.lobby().game().start();
        if (!success) {
            args.lobby().getGameSocketHandler().error(args.getPlayer(), "Not enough players.");
        }
    }
}
