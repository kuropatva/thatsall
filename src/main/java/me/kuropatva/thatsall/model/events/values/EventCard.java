package me.kuropatva.thatsall.model.events.values;

import me.kuropatva.thatsall.model.cards.Card;

public class EventCard extends EventValueType<Card> {

    private Card card;

    public EventCard(Card card) {
        this.card = card;
    }

    @Override
    public Card get() {
        return card;
    }

    @Override
    public void store(Card players) {
        this.card = players;
    }
}
