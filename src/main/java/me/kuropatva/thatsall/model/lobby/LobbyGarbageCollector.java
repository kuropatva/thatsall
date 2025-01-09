package me.kuropatva.thatsall.model.lobby;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;

public class LobbyGarbageCollector implements Runnable {

    private TemporalAmount TIMEOUT_TIME = Duration.ofMinutes(5);

    public LobbyGarbageCollector() {
    }

    // Constructor for tests
    public LobbyGarbageCollector(TemporalAmount timeout) {
        this.TIMEOUT_TIME = timeout;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            var timeoutTime = LocalDateTime.now().minus(TIMEOUT_TIME);
            ArrayList<String> lobbiesToClose = new ArrayList<>();
            LobbyManager.getGameMap().forEach((id, lobby) -> {
                if (lobby.getInactiveTime().isBefore(timeoutTime)) {
                    lobbiesToClose.add(id);
                }
            });
            lobbiesToClose.forEach(LobbyManager::close);
        }
    }
}
