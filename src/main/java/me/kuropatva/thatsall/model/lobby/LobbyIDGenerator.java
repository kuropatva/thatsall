package me.kuropatva.thatsall.model.lobby;

public class LobbyIDGenerator {

    private static int i = 0;

    public static String get() {
        return String.valueOf(i++);
    }

}
