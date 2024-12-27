package me.kuropatva.thatsall.model.player;

import me.kuropatva.thatsall.model.cards.Card;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

public class GamePlayerHand {

    private final LinkedList<Card> powerCards = new LinkedList<>();
    private final LinkedList<Integer> valueCards = new LinkedList<>();

    public void add(Card c) {
        powerCards.add(c);
    }

    public void add(int c) {
        valueCards.add(c);
    }

    public boolean remove(Card c) {
        return powerCards.remove(c);
    }

    public boolean remove(int c) {
        return valueCards.remove((Integer) c);
    }

    public Card getById(String id) {
        return powerCards.stream().filter(c -> Objects.equals(c.getID(), id)).findFirst().get();
    }

    public Optional<Integer> getValue(int id) {
        return valueCards.stream().filter(v -> id == v).findFirst();
    }

    public Optional<Card> getPower(String id) {
        return powerCards.stream().filter(p -> Objects.equals(p.getID(), id)).findFirst();
    }

    public int size() {
        return powerCards.size() + valueCards.size();
    }

    public String json() {
        return "\"value\": " + valueCards + ", \"power\": " + powerCards;
    }

    @Override
    public int hashCode() {
        return Objects.hash(powerCards, valueCards);
    }
}
