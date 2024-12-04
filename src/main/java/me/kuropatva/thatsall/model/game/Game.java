package me.kuropatva.thatsall.model.game;

import me.kuropatva.thatsall.model.cards.Card;
import me.kuropatva.thatsall.model.events.EventRegister;
import me.kuropatva.thatsall.model.lobby.Lobby;
import me.kuropatva.thatsall.model.player.Player;

import java.util.Collections;
import java.util.LinkedList;

public class Game {

    private final static int PLAYER_VALUE_CARDS = 5;
    private final static int PLAYER_POWER_CARDS = 5;

    private final Lobby lobby;
    private final EventRegister register = new EventRegister();
    private State state = State.WAIT;
    private LinkedList<Card> powerCard = new LinkedList<>();
    private LinkedList<Integer> valueCard = new LinkedList<>();
    private int playersNotReady = 0;

    public Game(Lobby lobby) {
        this.lobby = lobby;
    }


    public EventRegister eventRegister() {
        return register;
    }

    public void start() {
        state = State.ROUND;
        shuffleValueCards();
        shufflePowerCards();
        dealHands();
        playersNotReady = lobby.playerSize();
    }

    public void ready(Player player) {
        if (!player.gamePlayer().isReady()) {
            player.gamePlayer().setReady(true);
            if (--playersNotReady <= 0) endRound();
        }
    }

    public void endRound() {
        playersNotReady = lobby.playerSize();
    }

    private void shuffleValueCards() {
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 4; j++) {
                valueCard.push(i);
            }
        }
        Collections.shuffle(valueCard);
    }

    private void shufflePowerCards() {

    }

    private void dealHands() {
        lobby.players().forEach(p -> {
            var gP = p.gamePlayer();
            for (int i = 0; i < PLAYER_POWER_CARDS; i++) {
                gP.playerHand().add(valueCard.removeFirst());
                gP.playerHand().add(powerCard.removeFirst());
            }
        });
    }

    private enum State {
        WAIT,
        ROUND
    }
}
