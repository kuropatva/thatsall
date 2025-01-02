package me.kuropatva.thatsall.model.cards;

import me.kuropatva.thatsall.model.events.EventType;
import me.kuropatva.thatsall.view.Jsonable;

import java.util.Arrays;

public record CardData(int cost, EventType[] events, String name, String description) implements Jsonable {

    @Override
    public String toJson() {
        return "{\n\"cost\": " + cost + ",\n" +
                "\"events\": \"" + Arrays.toString(events) + " " + "\"\n," +
                "\"name\": \"" + name + "\",\n" +
                "\"description\": \"" + description + "\"\n}";
    }

    public String toJson(String id) {
        return "\"" + id + "\": " + "{" +
                "\"cost\": " + cost + ",\n" +
                "\"events\": \"" + Arrays.toString(events) + " " + "\"\n," +
                "\"name\": \"" + name + "\",\n" +
                "\"description\": \"" + description + "\"\n}";
    }
}
