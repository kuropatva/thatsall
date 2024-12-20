package me.kuropatva.thatsall.model.events.values;

import me.kuropatva.thatsall.model.cards.Card;
import me.kuropatva.thatsall.model.player.Player;

import java.util.ArrayList;

public class EventCardList extends EventValueType<ArrayList<Card>>{

    private ArrayList<Card> cards;

    public EventCardList(ArrayList<Card> cards) {
        this.cards = cards;
    }

    @Override
    public ArrayList<Card> get() {
        return cards;
    }

    @Override
    public void store(ArrayList<Card> players) {
        this.cards = players;
    }
}
