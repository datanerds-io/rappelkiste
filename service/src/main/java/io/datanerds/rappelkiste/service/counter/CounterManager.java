package io.datanerds.rappelkiste.service.counter;

import java.util.Collection;
import java.util.UUID;

public interface CounterManager {
    Collection<UUID> getCounters();
    long getCounterValue(UUID id);
    void createCounter(UUID id);
    void addValue(UUID id, long value);
}
