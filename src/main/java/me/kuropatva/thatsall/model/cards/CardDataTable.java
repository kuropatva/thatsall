package me.kuropatva.thatsall.model.cards;

import me.kuropatva.thatsall.model.events.EventType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class CardDataTable {

    private static final CardData NULL_CARD_DATA = new CardData(404, null, "Card not found", "For some reason data couldn't be loaded.");
    private static HashMap<String, CardData> map;

    static {
        init();
    }

    private CardDataTable() {
    }

    public static CardData get(String className) {
        return map.getOrDefault(className, NULL_CARD_DATA);
    }

    private static void init() {
        CardDataTable.map = new HashMap<>();
        long lineCounter = 0;
        try (var in = new BufferedReader(new InputStreamReader(Objects.requireNonNull(CardDataTable.class.getResourceAsStream("/powerCards/cards.yml"))))) {
            String line;
            while ((line = in.readLine()) != null) {
                lineCounter++;
                if (!line.isEmpty() && !line.startsWith("#")) {
                    DataBuilder.add(line);
                }
            }
            System.out.println(DataBuilder.buildCounter);
            if (DataBuilder.buildCounter != 5) throw new RuntimeException("cards.yml line number check failed.");
        } catch (Exception e) {
            throw new Error("CardDataTable error. Please verify cards.yml correctness, crashed at line " + lineCounter + ".\n" + e);
        }
    }

    private static class DataBuilder {
        private static String className;
        private static int cost;
        private static EventType[] events;
        private static String name;
        private static String description;

        private static int buildCounter = 5;

        public static void add(String line) {
            var split = line.split(": ");
            switch (split[0].strip().toUpperCase()) {
                case "COST" -> {
                    cost = Integer.parseInt(split[1]);
                    buildCounter--;
                }
                case "EVENTS" -> {
                    var splitEvents = split[1].split(", ");
                    events = Arrays.stream(splitEvents).map(EventType::valueOf).toArray(EventType[]::new);
                    buildCounter--;
                }
                case "NAME" -> {
                    name = split[1];
                    buildCounter--;
                }
                case "DESCRIPTION" -> {
                    description = split[1];
                    buildCounter--;
                }
                default -> {
                    className = split[0].replaceAll(":", "").replaceAll(" ", "");
                    buildCounter--;
                }
            }

            if (buildCounter == 0) {
                buildCounter = 5;
                flush();
            }
        }

        private static void flush() {
            map.put(className, new CardData(cost, events, name, description));
        }
    }

}
