package io.datanerds.rappelkiste.service.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CreateCounter extends CounterEvent {

    public CreateCounter(UUID counterId) {
        super(counterId);
    }

    @JsonCreator
    private CreateCounter(@JsonProperty("eventId") UUID eventId, @JsonProperty("counterId") UUID counterId) {
        super(eventId, counterId);
    }

}
