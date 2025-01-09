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
import me.kuropatva.thatsall.view.GameRoundInfo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

public class Game {

    // TODO: Extract constants to some sort of config
    private final static int PLAYER_VALUE_CARDS = 3;
    private final static int PLAYER_POWER_CARDS = 4;
    private final static int PLAYER_ROUND_VALUE_CARDS = 1;
    private final static int PLAYER_ROUND_POWER_CARDS = 1;
    private final static int GOLD_PER_ROUND = 5;
    private final static int GOLD_AT_START = 10;
    private final static int WIN_POINTS = 30;

    private final Lobby lobby;
    private final EventRegister register = new EventRegister();
    private State state = State.LOBBY;
    private final LinkedList<Integer> valueCard = new LinkedList<>();
    private final RandomPowerCardGenerator randomPowerCardGenerator = new RandomPowerCardGenerator();
    private int playersNotReady = 0;
    private int roundNumber = 0;
    private final GameRoundInfo roundInfo = new GameRoundInfo();

    public Game(Lobby lobby) {
        this.lobby = lobby;
    }


    public EventRegister eventRegister() {
        return register;
    }

    public synchronized boolean start() {
        lobby.updateInactive();
        if (lobby.playerSize() < 2) return false;
        if (state != State.LOBBY) return false;
        state = State.ROUND;
        dealHands();
        resetReadiness();
        roundNumber = 1;
        updateAllPlayers();
        return true;
    }

    public synchronized void ready(Player player) {
        lobby.updateInactive();
        if (!player.gamePlayer().isReady()) {
            player.gamePlayer().setReady(true);
            triggerEvent(EventType.ON_PLAYER_READY, Event.statelessEvent(player));
            player.gamePlayer().getPlayedPowerCards().forEach(card -> {
                roundInfo.logPowerCard(player, card);
                card.setOwner(player);
                eventRegister().register(card, card.getEvents());
            });
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
        } else { // draw
            triggerEvent(EventType.ON_DRAW, Event.statelessEvent());
        }

        //check game winner
        var highestPoints = new HighestValue();
        lobby.players().forEach(p -> highestPoints.add(p.gamePlayer().getPoints(), p));
        if (highestPoints.getValue() >= WIN_POINTS && highestPoints.getPlayer() != null) {
            gameWinner(highestPoints.getPlayer());
            return;
        }
        // finish round
        finishRound();
        for (Player player : lobby.players()) {
            lobby.getGameSocketHandler().finishRound(player, winner);
        }
        triggerEvent(EventType.ON_ROUND_FINISH, Event.statelessEvent());
        roundNumber++;
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
        roundInfo.flush(this);
    }

    private HighestValue getRoundWinner() {
        var highestScore = new HighestValue();
        lobby.players().forEach(p -> {
            highestScore.add(p.gamePlayer().getPlayedValueCard(), p);
            roundInfo.logValueCard(p, p.gamePlayer().getPlayedValueCard());
        });
        return highestScore;
    }

    private void resetReadiness() {
        playersNotReady = lobby.playerSize();
        lobby.players().forEach(p -> p.gamePlayer().setReady(false));
    }

    public void gameWinner(Player winner) {
        lobby.players().forEach(p -> lobby.getGameSocketHandler().finishGame(p, winner.username()));
        LobbyManager.close(lobby);
    }

    public void updateAllPlayers() {
        lobby.players().forEach(p -> lobby.getGameSocketHandler().refreshPlayer(p));
    }

    public Integer takeValueCard() {
        if (valueCard.isEmpty()) shuffleValueCards();
        return valueCard.removeFirst();
    }

    public Card takePowerCard() {
        return randomPowerCardGenerator.get();
    }

    private void shuffleValueCards() {
        for (int i = 1; i < 10; i++) {
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

    public GameRoundInfo getRoundInfo() {
        return roundInfo;
    }

    public void triggerEvent(EventType evenType, Event event) {
        eventRegister().trigger(evenType, this, event);
    }

    public void dealPowerCard(Player player) {
        var card = takePowerCard();
        var event = Event.builder().value("CARD_VALUE", new EventCard(card)).build();
        triggerEvent(EventType.ON_POWER_CARD_DEAL, event);
        card = (Card) event.getValue("CARD_VALUE").get();
        player.gamePlayer().playerHand().add(card);
    }

    public void dealValueCard(Player player) {
        if (valueCard.isEmpty()) shuffleValueCards();
        var card = takeValueCard();
        var event = Event.builder().value("INT_VALUE", new EventInt(card)).build();
        triggerEvent(EventType.ON_VALUE_CARD_DEAL, event);
        player.gamePlayer().playerHand().add((Integer) event.getValue("INT_VALUE").get());
    }

    public Lobby lobby() {
        return lobby;
    }

    public enum State {
        LOBBY, ROUND
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, valueCard, playersNotReady, getRoundNumber());
    }
}

