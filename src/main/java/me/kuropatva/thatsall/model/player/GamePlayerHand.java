package me.kuropatva.thatsall.model.player;

import me.kuropatva.thatsall.model.cards.Card;

import java.util.LinkedList;
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

    public boolean removeById(int id) {
        var card = powerCards.stream().filter(c -> c.getID() == id).findFirst();
        return card.map(powerCards::remove).orElse(false);
    }

    public Optional<Integer> getValue(int id) {
        return valueCards.stream().filter(v -> id == v).findFirst();
    }

    public Optional<Card> getPower(int id) {
        return powerCards.stream().filter(p -> p.getID() == id).findFirst();
    }

}
