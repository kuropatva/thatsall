package me.kuropatva.thatsall.model.cards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

public class RandomPowerCardGenerator {

    private final static String CARDS_PACKAGE_SLASH = "/me/kuropatva/thatsall/model/cards/concretcards";
    private final static String CARDS_PACKAGE_DOT = "me.kuropatva.thatsall.model.cards.concretcards.";
    private static final List<? extends Class<? extends Card>> cards = getClasses();
    private final Random random = new Random();

    private static List<? extends Class<? extends Card>> getClasses() {
        try (var input = RandomPowerCardGenerator.class.getResourceAsStream(CARDS_PACKAGE_SLASH)) {
            assert input != null;
            var reader = new BufferedReader(new InputStreamReader(input));
            return (reader.lines()
                    .filter(s -> s.endsWith(".class"))
                    .map(RandomPowerCardGenerator::toClass)
                    .toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void verify() {
        for (Class<? extends Card> card : cards) {
            if (CardDataTable.get(card.getSimpleName()) == CardDataTable.NULL_CARD_DATA) {
                throw new RuntimeException("Config.yml data missing for class " + card.getSimpleName() + " (cards.yml:1)");
            }
        }
    }

    @SuppressWarnings({"all"})
    private static Class<? extends Card> toClass(String className) {
        try {
            return (Class<? extends Card>) Class.forName(CARDS_PACKAGE_DOT + className.substring(0, className.lastIndexOf(".")));
        } catch (ClassNotFoundException | IndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        } catch (ClassCastException e) {
            throw new RuntimeException("Class is not a subclass of a card");
        }
    }

    public Card get() {
        try {
            return cards.get(random.nextInt(cards.size())).getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
