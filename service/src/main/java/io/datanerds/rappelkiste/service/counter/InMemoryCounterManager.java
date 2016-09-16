package io.datanerds.rappelkiste.service.counter;

import io.datanerds.rappelkiste.service.exception.CounterNotFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryCounterManager implements CounterManager {
    private final Map<UUID, AtomicLong> counters = new ConcurrentHashMap<>();

    @Override
    public Collection<UUID> getCounters() {
        return Collections.unmodifiableCollection(counters.keySet());
    }

    @Override
    public long getCounterValue(UUID id) {
        if (!counters.containsKey(id)) {
            throw new CounterNotFoundException(String.format("Counter %s does not exist.", id));
        }
        return counters.get(id).get();
    }

    @Override
    public void createCounter(UUID id) {
        counters.put(id, new AtomicLong(0));
    }

    @Override
    public void addValue(UUID id, long value) {
        counters.computeIfAbsent(id, k -> new AtomicLong(0)).addAndGet(value);
    }
}
