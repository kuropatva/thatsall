package me.kuropatva.thatsall.model.events.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventStringTest {

    @Test
    void get() {
        var val = new EventString("abc");
        assertEquals("abc", val.get());
    }

    @Test
    void store() {
        var val = new EventString("a");
        val.store("b");
        assertEquals("b", val.get());
        val.store(null);
        assertEquals(null, val.get());
    }
}