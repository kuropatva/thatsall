package me.kuropatva.thatsall.controller.commands;

import me.kuropatva.thatsall.controller.CommandArgs;

public class PlayValueCommand extends WebsocketCommand {
    @Override
    public void run(CommandArgs args) {
        var value = args.getArg(0);
        if (args.getPlayer().gamePlayer().isReady()) return;
        int card;
        try {
            card = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            args.lobby().getGameSocketHandler().error(args.getPlayer(), "Unknown Value Card value.");
            return;
        }
        var removed = args.getPlayer().gamePlayer().playerHand().remove(card);
        if (!removed) {
            args.lobby().getGameSocketHandler().error(args.getPlayer(), "Value card missing from hand");
            return;
        }
        args.getPlayer().gamePlayer().setPlayedValueCard(card);
        args.lobby().game().ready(args.getPlayer());
    }
}
