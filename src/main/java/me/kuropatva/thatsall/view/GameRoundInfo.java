package me.kuropatva.thatsall.view;

import me.kuropatva.thatsall.model.cards.Card;
import me.kuropatva.thatsall.model.game.Game;
import me.kuropatva.thatsall.model.player.Player;

import java.util.LinkedList;

public class GameRoundInfo {

    private final LinkedList<String> messages = new LinkedList<>();

    public void log(String string) {
        messages.addLast(string);
    }

    public void logValueCard(Player player, int value) {
        log("Player " + bold(player.username()) + " has played '" + bold(String.valueOf(value)) + "' Value Card");
    }

    public void logPowerCard(Player player, Card card) {
        log("Player " + bold(player.username()) + " has played " + bold(card.getName()) + " (" + card.getDescription() + ")");
    }

    public void flush(Game game) {
        var handler = game.lobby().getGameSocketHandler();
        for (Player player : game.lobby().players()) {
            messages.forEach(m -> handler.message(player, m));
        }
        messages.clear();
    }

    private String bold(String s) {
        return "<b>" + s + "</b>";
    }
}
