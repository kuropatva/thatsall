package me.kuropatva.thatsall.model.game;

import me.kuropatva.thatsall.model.cards.Card;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPowerCardGenerator {

    private final static String CARDS_PACKAGE_SLASH = "me/kuropatva/thatsall/model/cards/concretcards";
    private final static String CARDS_PACKAGE_DOT = (CARDS_PACKAGE_SLASH + '.').replaceAll("/", ".");
    private final static List<? extends Class<? extends Card>> cards = getClasses();
    private Random random = new Random();

    public RandomPowerCardGenerator() {

    }

    public Card get() {
        try {
            return cards.get(random.nextInt(cards.size())).getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<? extends Class<? extends Card>> getClasses() {
        try (var input = ClassLoader.getSystemClassLoader().getResourceAsStream(CARDS_PACKAGE_SLASH)) {
            assert input != null;
            var reader = new BufferedReader(new InputStreamReader(input));
            return (reader.lines()
                    .filter(l -> l.endsWith(".class"))
                    .map(RandomPowerCardGenerator::toClass)
                    .toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({"all"})
    private static Class<? extends Card> toClass(String className) {
        try {
            return (Class<? extends Card>) Class.forName(CARDS_PACKAGE_DOT + className.substring(0, className.lastIndexOf(".")));
        } catch (ClassNotFoundException | IndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        } catch (ClassCastException e) {
            throw new RuntimeException("Cass is not a subclass of card");
        }
    }
}
