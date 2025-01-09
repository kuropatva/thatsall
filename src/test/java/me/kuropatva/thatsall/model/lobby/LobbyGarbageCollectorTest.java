package me.kuropatva.thatsall.model.lobby;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LobbyGarbageCollectorTest {
    @Test
    public void LobbyGarbageCollectorCloseTest() throws InterruptedException {
        var duration = Duration.ofSeconds(1);
        LobbyManager.newGame("1");
        var testCollector = new LobbyGarbageCollector(duration);
        var thread = new Thread(testCollector);
        thread.start();
        Thread.sleep(Duration.ofSeconds(2));
        assertTrue(LobbyManager.getGameMap().isEmpty());
    }

}