package me.kuropatva.thatsall.model.game;

import me.kuropatva.thatsall.model.cards.Card;
import me.kuropatva.thatsall.model.cards.RandomPowerCardGenerator;
import me.kuropatva.thatsall.model.events.Event;
import me.kuropatva.thatsall.model.events.EventRegister;
import me.kuropatva.thatsall.model.events.EventType;
import me.kuropatva.thatsall.model.events.values.EventCard;
import me.kuropatva.thatsall.model.events.values.EventInt;
import me.kuropatva.thatsall.model.lobby.Lobby;
import me.kuropatva.thatsall.model.lobby.LobbyManager;
import me.kuropatva.thatsall.model.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Game {

    private final static int PLAYER_VALUE_CARDS = 5;
    private final static int PLAYER_POWER_CARDS = 5;
    private final static int PLAYER_ROUND_VALUE_CARDS = 1;
    private final static int PLAYER_ROUND_POWER_CARDS = 1;
    private final static int GOLD_PER_ROUND = 5;
    private final static int GOLD_AT_START = 10;
    private final static int WIN_POINTS = 20;

    private final Lobby lobby;
    private final EventRegister register = new EventRegister();
    private State state = State.WAIT;
    private final LinkedList<Integer> valueCard = new LinkedList<>();
    private final RandomPowerCardGenerator randomPowerCardGenerator = new RandomPowerCardGenerator();
    private int playersNotReady = 0;
    private int roundNumber = 0;

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
        roundNumber = 1;
    }

    public void ready(Player player) {
        if (!player.gamePlayer().isReady()) {
            player.gamePlayer().setReady(true);
            triggerEvent(EventType.ON_ROUND_READY, Event.statelessEvent(player));
            if (--playersNotReady <= 0) endRound();
        }
    }

    public void endRound() {
        // resetting player ready counter
        resetReadiness();
        // trigger events
        triggerEvent(EventType.ON_ROUND_READY, Event.statelessEvent());
        // determining the winner
        var winner = getRoundWinner().getPlayer();
        // perform actions
        if (winner != null) {
            // events
            var eventWin = Event.builder().player(winner).value("INT_POINTS", new EventInt(2)).build();
            triggerEvent(EventType.ON_WIN, eventWin);
            // add point
            winner.gamePlayer().addPoints((int) eventWin.getValue("INT_POINTS").get());

            // losers event
            var losers = new ArrayList<Player>();
            lobby.players().forEach(p -> {
                if (p != winner) losers.add(p);
            });
        } else { // draw
            triggerEvent(EventType.ON_DRAW, Event.statelessEvent());
        }
        lobby.getGameSocketHandler().finishRound(winner);

        //check game winner
        var highestPoints = new HighestValue();
        lobby.players().forEach(p -> highestPoints.add(p.gamePlayer().getPoints(), p));
        if (highestPoints.getValue() >= WIN_POINTS && highestPoints.getPlayer() != null) {
            gameWinner(highestPoints.getPlayer());
            return;
        }
        // finish round
        roundNumber++;
        finishRound();
        triggerEvent(EventType.ON_ROUND_FINISH, Event.statelessEvent());
        // update players
        updateAllPlayers();
    }

    private void finishRound() {
        for (Player player : lobby.players()) {
            player.gamePlayer().addGold(GOLD_PER_ROUND);
            player.gamePlayer().setPlayedValueCard(-1);
            player.gamePlayer().resetPlayedPowerCard();
            for (int i = 0; i < PLAYER_ROUND_VALUE_CARDS; i++) {
                dealValueCard(player);
            }
            for (int i = 0; i < PLAYER_ROUND_POWER_CARDS; i++) {
                dealPowerCard(player);
            }
        }
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
        lobby.players().forEach(p -> lobby.getGameSocketHandler().finishGame(p, winner.username()));
        LobbyManager.close(lobby);
    }

    private void updateAllPlayers() {
        lobby.players().forEach(p -> lobby.getGameSocketHandler().refresh(p));
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
            gP.setGold(GOLD_AT_START);
            for (int i = 0; i < PLAYER_POWER_CARDS; i++) {
                if (valueCard.isEmpty()) shuffleValueCards();
                gP.playerHand().add(takeValueCard());
            }
            for (int i = 0; i < PLAYER_VALUE_CARDS; i++) {
                gP.playerHand().add(takePowerCard());
            }
        });
    }

    public State state() {
        return state;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void triggerEvent(EventType evenType, Event event) {
        eventRegister().trigger(evenType, this, event);
    }

    public enum State {
        WAIT, ROUND
    }

    public void dealValueCard(Player player) {
        if (valueCard.isEmpty()) shuffleValueCards();
        var card = takeValueCard();
        var event = Event.builder().value("INT_VALUE", new EventInt(card)).build();
        triggerEvent(EventType.ON_VALUE_CARD_DEAL, event);
        player.gamePlayer().playerHand().add((Integer) event.getValue("INT_VALUE").get());
    }

    public void dealPowerCard(Player player) {
        var card = takePowerCard();
        var event = Event.builder().value("CARD_VALUE", new EventCard(card)).build();
        triggerEvent(EventType.ON_POWER_CARD_DEAL, event);
        player.gamePlayer().playerHand().add(takePowerCard());
    }
}

