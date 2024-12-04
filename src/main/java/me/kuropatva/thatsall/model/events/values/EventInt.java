package me.kuropatva.thatsall.model.events.values;

public class EventInt extends EventValueType<Integer> {

    private Integer value;

    public EventInt(int value) {
        this.value = value;
    }

    @Override
    public Integer get() {
        return value;
    }

    @Override
    public void store(Integer integer) {
        this.value = integer;
    }
}
