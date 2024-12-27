package me.kuropatva.thatsall.model.cards;

import me.kuropatva.thatsall.model.events.EventType;

public record CardData(int cost, EventType[] events, String name, String description) {
}
