package me.kuropatva.thatsall.model.cards;

import me.kuropatva.thatsall.model.events.EventType;

import java.util.Arrays;

public record CardData(int cost, EventType[] events, String name, String description) {

    public String toJson() {
        return "{\n\"cost\": " + cost + ",\n" +
                "\"events\": \"" + Arrays.toString(events) + " " + "\"\n," +
                "\"name\": \"" + name + "\",\n" +
                "\"description\": \"" + description + "\"\n}";
    }
}
