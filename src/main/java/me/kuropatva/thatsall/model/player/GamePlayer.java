package me.kuropatva.thatsall.model.player;

import me.kuropatva.thatsall.model.cards.Card;

import java.util.LinkedList;
import java.util.Objects;

public class GamePlayer {

    private final GamePlayerHand playerHand = new GamePlayerHand();
    private int points = 0;
    private int gold = 0;
    private boolean ready = false;

    private int playedValueCard = -1;
    private final LinkedList<Card> playedPowerCards = new LinkedList<>();


    public int getPoints() {
        return points;
    }

    public void addPoints(int i) {
        points += i;
    }

    public void setPoints(int i) {
        points = i;
    }

    public GamePlayerHand playerHand() {
        return playerHand;
    }

    public boolean isReady() {
        return ready;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void addGold(int gold) {
        this.gold += gold;
    }

    public boolean attemptRemoveGold(int gold) {
        if (this.gold < gold) return false;
        this.gold -= gold;
        return true;
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

    public void modifyPlayedValueCard(int value) {
        if (value < 1) this.playedValueCard = 1;
        else if (value > 10) this.playedValueCard = 10;
        else this.playedValueCard = value;
    }

    public void addPlayedPowerCard(Card c) {
        playedPowerCards.add(c);
    }

    public void resetPlayedPowerCard() {
        playedPowerCards.clear();
    }

    public LinkedList<Card> getPlayedPowerCards() {
        return playedPowerCards;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerHand, getPoints(), getGold(), isReady(), getPlayedValueCard(), getPlayedPowerCards());
    }
}
