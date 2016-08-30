package io.datanerds.rappelkiste.service.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import java.util.Objects;
import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "operation")
@JsonSubTypes({
        @Type(value = CreateCounter.class, name = "create"),
        @Type(value = AddValueToCounter.class, name = "add") })
public abstract class CounterEvent {
    public final UUID counterId;
    public final UUID eventId;

    protected CounterEvent(UUID counterId) {
        this(UUID.randomUUID(), counterId);
    }

    protected CounterEvent(UUID eventId, UUID counterId) {
        this.counterId = counterId;
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CounterEvent)) return false;
        CounterEvent that = (CounterEvent) o;
        return Objects.equals(counterId, that.counterId) &&
                Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(counterId, eventId);
    }
}
