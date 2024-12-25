package me.kuropatva.thatsall.controller.commands;

import me.kuropatva.thatsall.controller.CommandArgs;

public class PlayPowerCommand extends WebsocketCommand {
    public void run(CommandArgs args) {
        var value = args.getValue("");
        int card;
        try {
            card = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            args.lobby().getGameSocketHandler().error(args.getPlayer(), "Unknown Power Card ID.");
            return;
        }
        var removed = args.getPlayer().gamePlayer().playerHand().removeById(card);
        if (!removed) args.lobby().getGameSocketHandler().error(args.getPlayer(), "Power card missing from hand");
        args.getPlayer().gamePlayer().setPlayedValueCard(card);
        args.lobby().game().ready(args.getPlayer());
    }
}
