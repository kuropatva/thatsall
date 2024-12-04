package me.kuropatva.thatsall.model.events;

import me.kuropatva.thatsall.model.events.values.EventInt;
import me.kuropatva.thatsall.model.events.values.EventString;
import me.kuropatva.thatsall.model.events.values.EventValueType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventRegisterTest {

    @Test
    void trigger() {
        var register = new EventRegister();
        var sheep = new ListenerSheep();

        register.register(sheep, EventType.ON_WIN);

        var eventRemove = EventBuilder.get().value("int_remove", new EventInt(1)).build();
        var eventNoRemove = EventBuilder.get().value("int_remove", new EventInt(0)).build();
        var eventWrongValueType = EventBuilder.get().value("int_remove", new EventString("a")).build();

        register.trigger(EventType.ON_LOSE, eventNoRemove);
        assertFalse(sheep.triggered());
        register.trigger(EventType.ON_WIN, eventNoRemove);
        assertTrue(sheep.triggered());
        sheep.reset();
        register.trigger(EventType.ON_WIN, eventRemove);
        assertTrue(sheep.triggered());
        sheep.reset();
        register.trigger(EventType.ON_WIN, eventNoRemove);
        assertFalse(sheep.triggered());

        assertThrows(ClassCastException.class, () -> sheep.trigger(eventWrongValueType));
    }

    @Test
    void modify() {
        var sheep = new ListenerSheep();
        var eventModify = EventBuilder.get().value("int_modify", new EventInt(1)).build();
        sheep.trigger(eventModify);

        assertEquals(2, eventModify.getValue("int_modify").get());
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
    public boolean trigger(Event event) {
        triggered = true;

        EventInt modify = (EventInt) event.getValue("int_modify");
        if (modify != null && modify.get()  == 1) {
            modify.store(2);
        }

        var remove = event.getValue("int_remove");
        if (remove != null && (int) remove.get() == 1) {
            return true;
        }

        return false;
    }
}