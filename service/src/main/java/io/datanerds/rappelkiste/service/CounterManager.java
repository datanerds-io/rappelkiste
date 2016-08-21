package io.datanerds.rappelkiste.service;

import io.datanerds.rappelkiste.service.events.AddValueToCounter;
import io.datanerds.rappelkiste.service.events.CreateCounter;
import io.datanerds.rappelkiste.service.events.CounterEvent;
import io.datanerds.rappelkiste.service.events.EventQueue.EventHandler;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class CounterManager implements EventHandler, CounterAccess {
    private final Map<UUID, AtomicLong> counters = new ConcurrentHashMap<>();

    @Override
    public Collection<UUID> getCounters() {
        return Collections.unmodifiableCollection(counters.keySet());
    }

    @Override
    public long getCounterValue(UUID id) {
        return counters.get(id).get();
    }

    @Override
    public <T extends CounterEvent> void handle(T event) {
        if (event instanceof CreateCounter) {
            createCounter((CreateCounter) event);
        } else if (event instanceof AddValueToCounter) {
            addValue((AddValueToCounter) event);
        }
    }

    private void createCounter(CreateCounter create) {
        counters.put(create.counterId, new AtomicLong(0));
    }

    private void addValue(AddValueToCounter add) {
        counters.get(add.counterId).addAndGet(add.value);
    }
}
