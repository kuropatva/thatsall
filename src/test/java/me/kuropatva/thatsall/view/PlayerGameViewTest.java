package me.kuropatva.thatsall.view;

import me.kuropatva.thatsall.model.lobby.Lobby;
import me.kuropatva.thatsall.model.player.Player;
import me.kuropatva.thatsall.model.player.PlayerCredentials;
import org.junit.jupiter.api.Test;

class PlayerGameViewTest {
    @Test
    public void viewTest() {
        Lobby lobby = new Lobby();
        var p1 = new Player(new PlayerCredentials("abc", "a"));
        var p2 = new Player(new PlayerCredentials("123", "a"));
        lobby.addPlayer(p1);
        lobby.addPlayer(p2);
        lobby.game().start();
        var view = new PlayerGameView(lobby);
        System.out.println(view.getJson(p1));
        System.out.println(view.getJson(p2));
    }

}