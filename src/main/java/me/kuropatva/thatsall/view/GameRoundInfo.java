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
        log(player.username() + " has played <b>'" + value + "'</b> Value Card");
    }

    public void logPowerCard(Player player, Card card) {
        log(player.username() + " has played <b>" + card.getName() + "</b> (" + card.getDescription() + ")");
    }

    public void flush(Game game) {
        var handler = game.lobby().getGameSocketHandler();
        for (Player player : game.lobby().players()) {
            messages.forEach(m -> handler.message(player, m));
        }
        messages.clear();
    }
}
