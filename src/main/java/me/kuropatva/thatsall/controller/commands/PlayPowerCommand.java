package me.kuropatva.thatsall.controller.commands;

import me.kuropatva.thatsall.controller.CommandArgs;

public class PlayPowerCommand extends WebsocketCommand {
    public void run(CommandArgs args) {
        var value = args.getValue("");
        var player = args.getPlayer();
        var socket = args.lobby().getGameSocketHandler();
        int cardID;
        try {
            cardID = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            socket.error(player, "Unknown Power Card ID.");
            return;
        }
        var card = player.gamePlayer().playerHand().getById(cardID);
        if (card == null) socket.error(player, "Power card missing from hand");
        if (player.gamePlayer().attemptRemoveGold(card.getCost()))
            socket.error(player, "Insufficient gold to play power card."); // TODO: Specify card
        player.gamePlayer().playerHand().remove(card);
        player.gamePlayer().addPlayedPowerCard(card);
        args.lobby().game().ready(player);
    }
}
