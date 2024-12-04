package me.kuropatva.thatsall.model.events.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventIntTest {

    @Test
    void get() {
        var val = new EventInt(123);
        assertEquals(123, val.get());
    }

    @Test
    void store() {
        var val = new EventInt(0);
        val.store(123);
        assertEquals(123, val.get());
    }
}