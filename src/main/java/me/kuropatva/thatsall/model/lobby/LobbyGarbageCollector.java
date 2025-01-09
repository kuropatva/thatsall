package me.kuropatva.thatsall.model.lobby;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;

public class LobbyGarbageCollector implements Runnable {

    private static final Logger log = LogManager.getLogger(LobbyGarbageCollector.class);
    private TemporalAmount TIMEOUT_TIME = Duration.ofMinutes(5);

    public LobbyGarbageCollector() {
    }

    // Constructor for tests
    public LobbyGarbageCollector(TemporalAmount timeout) {
        this.TIMEOUT_TIME = timeout;
    }

    @Override
    @SuppressWarnings("logging")
    public void run() {
        while (!Thread.interrupted()) {
            try {
                var timeoutTime = LocalDateTime.now().minus(TIMEOUT_TIME);
                ArrayList<String> lobbiesToClose = new ArrayList<>();
                LobbyManager.getGameMap().forEach((id, lobby) -> {
                    if (lobby.getInactiveTime().isBefore(timeoutTime)) {
                        lobbiesToClose.add(id);
                    }
                });
                lobbiesToClose.forEach(LobbyManager::close);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }
}
