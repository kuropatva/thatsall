package me.kuropatva.thatsall.controller.commands;

import me.kuropatva.thatsall.controller.CommandArgs;

public class PlayPowerCommand extends WebsocketCommand {
    public void run(CommandArgs args) {
        if (args.getPlayer().gamePlayer().isReady()) return;
        var cardID = args.getArg(0);
        var player = args.getPlayer();
        var socket = args.lobby().getGameSocketHandler();
        var card = player.gamePlayer().playerHand().getById(cardID);
        if (card == null) {
            socket.error(player, "Power card missing from hand");
            return;
        }
        if (!player.gamePlayer().attemptRemoveGold(card.getCost())) { // TODO: Specify card
            socket.error(player, "Insufficient gold to play power card.");
            return;
        }
        player.gamePlayer().playerHand().remove(card);
        player.gamePlayer().addPlayedPowerCard(card);
    }
}
