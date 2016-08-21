package io.datanerds.rappelkiste.service.events;

import java.util.UUID;

public class CounterEvent {
    public final UUID counterId;
    public final UUID eventId;


    public CounterEvent(UUID counterId) {
        this.counterId = counterId;
        this.eventId = UUID.randomUUID();
    }
}
