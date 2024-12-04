package me.kuropatva.thatsall.model.events.values;

public class EventString extends EventValueType<String> {

    private String value;

    public EventString(String value) {
        this.value = value;
    }

    @Override
    public String get() {
        return value;
    }

    @Override
    public void store(String string) {
        this.value = string;
    }
}
