package me.kuropatva.thatsall.model.events;

import me.kuropatva.thatsall.model.events.values.EventInt;
import me.kuropatva.thatsall.model.events.values.EventString;
import me.kuropatva.thatsall.model.game.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventRegisterTest {

    @Test
    void trigger() {
        var register = new EventRegister();
        var sheep = new ListenerSheep();

        register.register(sheep, EventType.ON_WIN);
        register.register(new ListenerSheep(), EventType.ON_WIN);

        var eventRemove = Event.builder().value("int_remove", new EventInt(1)).build();
        var eventNoRemove = Event.builder().value("int_remove", new EventInt(0)).build();
        var eventWrongValueType = Event.builder().value("int_remove", new EventString("a")).build();

        register.trigger(EventType.ON_ROUND_READY, null, eventNoRemove);
        assertFalse(sheep.triggered());
        register.trigger(EventType.ON_WIN, null, eventNoRemove);
        assertTrue(sheep.triggered());
        sheep.reset();
        register.trigger(EventType.ON_WIN, null, eventRemove);
        assertTrue(sheep.triggered());
        sheep.reset();
        register.trigger(EventType.ON_WIN, null, eventNoRemove);
        assertFalse(sheep.triggered());

        assertThrows(ClassCastException.class, () -> sheep.trigger(null, eventWrongValueType));
    }

    @Test
    void modify() {
        var sheep = new ListenerSheep();
        var eventModify = Event.builder().value("int_modify", new EventInt(1)).build();
        sheep.trigger(null, eventModify);

        assertEquals(2, eventModify.getValue("int_modify").get());
    }

    @Test
    void setValue() {
        var event = Event.builder().value("int_remove", new EventInt(1)).build();
        event.setValue("int_remove", new EventInt(5));
        assertEquals(5, event.getValue("int_remove").get());
        assertDoesNotThrow(() -> event.setValue(null, new EventInt(1)));
        assertDoesNotThrow(() -> event.setValue("int_remove", null));
        assertDoesNotThrow(() -> event.setValue("int_new", new EventInt(1)));
        assertEquals(1, event.getValue("int_new").get());
    }

}

class ListenerSheep implements EventListener {

    private boolean triggered = false;

    public boolean triggered() {
        return triggered;
    }

    public void reset() {
        this.triggered = false;
    }

    @Override
    public boolean trigger(Game game, Event event) {
        triggered = true;

        EventInt modify = (EventInt) event.getValue("int_modify");
        if (modify != null && modify.get()  == 1) {
            modify.store(2);
        }

        var remove = event.getValue("int_remove");
        return remove != null && (int) remove.get() == 1;
    }
}