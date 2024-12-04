package me.kuropatva.thatsall.player;

import me.kuropatva.thatsall.model.cards.Card;

import java.util.LinkedList;

public class GamePlayer {

    private final GamePlayerHand playerHand = new GamePlayerHand();
    private int points = 0;
    private boolean ready = false;

    private int playedValueCard = -1;
    private LinkedList<Card> playedPowerCards = new LinkedList<>();


    public int getPoints() {
        return points;
    }

    public GamePlayerHand playerHand() {
        return playerHand;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public int getPlayedValueCard() {
        return playedValueCard;
    }

    public void setPlayedValueCard(int playedValueCard) {
        this.playedValueCard = playedValueCard;
    }

    public void addPlayerPowerCard(Card c) {
        playedPowerCards.add(c);
    }

    public LinkedList<Card> getPlayedPowerCards() {
        return playedPowerCards;
    }
}
