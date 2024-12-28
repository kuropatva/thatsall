package me.kuropatva.thatsall.model.game;

import me.kuropatva.thatsall.model.cards.Card;
import me.kuropatva.thatsall.model.events.Event;
import me.kuropatva.thatsall.model.events.EventListener;
import me.kuropatva.thatsall.model.events.EventType;
import me.kuropatva.thatsall.model.lobby.Lobby;
import me.kuropatva.thatsall.model.player.Player;
import me.kuropatva.thatsall.model.player.PlayerCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Lobby lobby;
    private Game game;
    private List<Player> realPlayers;

    @BeforeEach
    void setUp() {
        // Initialize a real Lobby with players
        lobby = new Lobby();

        // Create real Players with PlayerCredentials
        realPlayers = IntStream.range(0, 4)
                .mapToObj(i -> new Player(new PlayerCredentials("Player" + i, "passcode" + i)))
                .collect(Collectors.toList());

        // Add players to the lobby
        realPlayers.forEach(lobby::addPlayer);

        // Initialize the game with the lobby
        game = new Game(lobby);
    }

    @Test
    void testStartInitializesGameCorrectly() {
        game.start();

        assertEquals(Game.State.ROUND, game.state());
        assertEquals(1, game.getRoundNumber());

        for (Player player : realPlayers) {
            assertEquals(10, player.gamePlayer().getGold());
            assertEquals(10, player.gamePlayer().playerHand().size());
        }
    }

    @Test
    void testReadyDecreasesPlayersNotReady() {
        game.start();
        Player player = realPlayers.get(0);

        game.ready(player);

        assertTrue(player.gamePlayer().isReady());
    }

    @Test
    void testEndRoundTriggersEventsAndResetsState() {
        game.start();
        AtomicBoolean t = new AtomicBoolean(false);
        EventListener listener = ((i0, i1) -> {
            t.set(true);
            return true;
        });
        game.eventRegister().register(listener, EventType.ON_ROUND_FINISH);
        realPlayers.forEach(p -> p.gamePlayer().setGold(0));
        game.endRound();
        assertTrue(t.get());

        for (Player player : realPlayers) {
            assertTrue(player.gamePlayer().getGold() > 0);
            assertEquals(-1, player.gamePlayer().getPlayedValueCard());
        }
    }

    @Test
    void testTakeValueCardShufflesWhenEmpty() {
        for (int i = 0; i < 53; i++) {
            game.takeValueCard();
        }

        assertNotNull(game.takeValueCard()); // Ensure cards are shuffled and added
    }

    @Test
    void testTakePowerCardReturnsCard() {
        Card card = game.takePowerCard();
        assertNotNull(card);
    }

    @Test
    void testDealValueCardAddsCardToPlayerHand() {
        Player player = realPlayers.get(0);
        int initialHandSize = player.gamePlayer().playerHand().size();

        game.dealValueCard(player);

        assertEquals(initialHandSize + 1, player.gamePlayer().playerHand().size());
    }

    @Test
    void testDealPowerCardAddsCardToPlayerHand() {
        Player player = realPlayers.get(0);
        int initialHandSize = player.gamePlayer().playerHand().size();

        game.dealPowerCard(player);

        assertEquals(initialHandSize + 1, player.gamePlayer().playerHand().size());
    }

    @Test
    void testGameWinnerClosesLobbyAndNotifiesPlayers() {
        Player winner = realPlayers.get(0);
        game.gameWinner(winner);

        for (Player player : realPlayers) {
            assertTrue(player.setLobby() == null || player.setLobby() != lobby);
        }
    }


    @Test
    void testTriggerEvent() {
        Event event = Event.statelessEvent();
        game.triggerEvent(EventType.ON_ROUND_FINISH, event);

    }
}
