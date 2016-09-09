package io.datanerds.rappelkiste.service.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.UUID;

public class AddValueToCounter extends CounterEvent {

    public final long value;

    public AddValueToCounter(UUID counterId, long value) {
        super(counterId);
        this.value = value;
    }

    @JsonCreator
    private AddValueToCounter(@JsonProperty("eventId") UUID eventId, @JsonProperty("counterId") UUID counterId,
            @JsonProperty("value") long value) {
        super(eventId, counterId);
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddValueToCounter)) return false;
        if (!super.equals(o)) return false;
        AddValueToCounter that = (AddValueToCounter) o;
        return value == that.value &&
                Objects.equals(counterId, that.counterId) &&
                Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), counterId, eventId, value);
    }
}
