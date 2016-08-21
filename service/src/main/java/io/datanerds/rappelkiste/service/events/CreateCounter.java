package io.datanerds.rappelkiste.service.events;

import java.util.UUID;

public class CreateCounter extends CounterEvent {

    public CreateCounter(UUID counterId) {
        super(counterId);
    }
}
