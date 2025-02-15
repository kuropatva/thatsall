package me.kuropatva.thatsall.model.events;

import me.kuropatva.thatsall.model.events.values.EventInt;
import me.kuropatva.thatsall.model.events.values.EventString;
import me.kuropatva.thatsall.model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EventBuilderAndEventTest {
    @Test
    public void eventBuilderAndObjectTest() {
        var testPlayer = new Player(null);
        var event = Event.builder().player(testPlayer)
                .value("a", new EventInt(1)).value("b", new EventInt(2))
                .value("c", new EventString("3")).value("d", new EventString("4"))
                .build();
        assertEquals(testPlayer, event.getPlayer());
        assertEquals(1, event.getValue("a").get());
        assertEquals(2, event.getValue("b").get());
        assertEquals("3", event.getValue("c").get());
        assertEquals("4", event.getValue("d").get());
    }

    @Test
    public void eventBuilderNullName() {
        assertThrows(NullPointerException.class, () -> Event.builder().value(null, new EventInt(1)));
    }
}