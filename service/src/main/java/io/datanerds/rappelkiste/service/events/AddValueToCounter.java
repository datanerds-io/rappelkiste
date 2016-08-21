package io.datanerds.rappelkiste.service.events;

import java.util.UUID;

public class AddValueToCounter extends CounterEvent {

    public final long value;

    public AddValueToCounter(UUID counterId, long value) {
        super(counterId);
        this.value = value;
    }
}
