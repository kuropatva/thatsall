package me.kuropatva.thatsall.model.player;

import me.kuropatva.thatsall.model.cards.Card;

import java.util.LinkedList;
import java.util.Optional;

public class GamePlayerHand {

    private LinkedList<Card> powerCards = new LinkedList<>();
    private LinkedList<Integer> valueCards = new LinkedList<>();

    public void add(Card c) {
        powerCards.add(c);
    }

    public void add(int c) {
        valueCards.add(c);
    }

    public void remove(Card c) {
        powerCards.remove(c);
    }

    public void remove(int c) {
        valueCards.remove((Integer) c);
    }

    public Optional<Integer> getValue(int id) {
        return valueCards.stream().filter(v -> id == v).findFirst();
    }

    public Optional<Card> getPower(int id) {
        return powerCards.stream().filter(p -> p.getID() == id).findFirst();
    }

}
