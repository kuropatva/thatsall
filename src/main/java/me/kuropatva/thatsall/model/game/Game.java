package me.kuropatva.thatsall.model.game;

import me.kuropatva.thatsall.model.cards.Card;
import me.kuropatva.thatsall.model.events.EventBuilder;
import me.kuropatva.thatsall.model.events.EventRegister;
import me.kuropatva.thatsall.model.events.EventType;
import me.kuropatva.thatsall.model.events.values.EventInt;
import me.kuropatva.thatsall.model.events.values.EventPlayerList;
import me.kuropatva.thatsall.model.lobby.Lobby;
import me.kuropatva.thatsall.model.lobby.LobbyManager;
import me.kuropatva.thatsall.model.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Game {

    private final static int PLAYER_VALUE_CARDS = 5;
    private final static int PLAYER_POWER_CARDS = 5;
    private final static int WIN_POINTS = 20;

    private final Lobby lobby;
    private final EventRegister register = new EventRegister();
    private State state = State.WAIT;
    private final LinkedList<Integer> valueCard = new LinkedList<>();
    private final RandomPowerCardGenerator randomPowerCardGenerator = new RandomPowerCardGenerator();
    private int playersNotReady = 0;

    public Game(Lobby lobby) {
        this.lobby = lobby;
    }


    public EventRegister eventRegister() {
        return register;
    }

    public void start() {
        if (lobby.playerSize() < 2) return;
        state = State.ROUND;
        dealHands();
        resetReadiness();
    }

    public void ready(Player player) {
        if (!player.gamePlayer().isReady()) {
            player.gamePlayer().setReady(true);
            if (--playersNotReady <= 0) endRound();
        }
    }

    public void endRound() {
        // resetting player ready counter
        resetReadiness();
        // trigger events
        var event = EventBuilder.get().game(lobby.game())
                .build();
        eventRegister().trigger(EventType.ON_ROUND_READY, event);
        // determining the winner
        var winner = getRoundWinner().getPlayer();

        // perform actions
        if (winner != null) {
            // events
            var eventWin = EventBuilder.get().player(winner).game(this).value("INT_POINTS", new EventInt(2)).build();
            eventRegister().trigger(EventType.ON_WIN, eventWin);
            // add point
            winner.gamePlayer().addPoints((int) eventWin.getValue("INT_POINTS").get());
            // losers event
            var losers = new ArrayList<Player>();
            lobby.players().forEach(p -> {
                if (p != winner) losers.add(p);
            });
            var eventLose = EventBuilder.get().player(winner).game(this).value("PLAYERLIST_LOSERS", new EventPlayerList(losers)).build();
            eventRegister().trigger(EventType.ON_LOSE, eventLose);
        }

        //check game winner
        var highestPoints = new HighestValue();
        lobby.players().forEach(p -> highestPoints.add(p.gamePlayer().getPoints(), p));
        if (highestPoints.getValue() >= WIN_POINTS && highestPoints.getPlayer() != null) {
            gameWinner(highestPoints.getPlayer());
            return;
        }
        // update players
        updateAllPlayers();
    }

    private HighestValue getRoundWinner() {
        var highestScore = new HighestValue();
        lobby.players().forEach(p -> highestScore.add(p.gamePlayer().getPlayedValueCard(), p));
        return highestScore;
    }

    private void resetReadiness() {
        playersNotReady = lobby.playerSize();
        lobby.players().forEach(p -> p.gamePlayer().setReady(false));
    }

    private void gameWinner(Player winner) {
        lobby.players().forEach(p -> p.sendMessage("FNG " + winner.username())); // TODO: extract
        LobbyManager.close(lobby);
    }

    private void updateAllPlayers() {
        // TODO
        // lobby.players().forEach(p -> p.sendMessage(...));
    }

    public Integer takeValueCard() {
        if (valueCard.isEmpty()) shuffleValueCards();
        return valueCard.removeFirst();
    }

    public Card takePowerCard() {
        return randomPowerCardGenerator.get();
    }

    private void shuffleValueCards() {
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 4; j++) {
                valueCard.push(i);
            }
        }
        Collections.shuffle(valueCard);
    }

    private void dealHands() {
        lobby.players().forEach(p -> {
            var gP = p.gamePlayer();
            for (int i = 0; i < PLAYER_POWER_CARDS; i++) {
                if (valueCard.isEmpty()) shuffleValueCards();
                gP.playerHand().add(takePowerCard());
            }
            for (int i = 0; i < PLAYER_VALUE_CARDS; i++) {
                gP.playerHand().add(takeValueCard());
            }
        });
    }

    public State state() {
        return state;
    }

    private enum State {
        WAIT, ROUND
    }
}

