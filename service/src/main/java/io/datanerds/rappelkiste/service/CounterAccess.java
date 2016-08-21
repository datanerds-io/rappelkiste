package io.datanerds.rappelkiste.service;

import java.util.Collection;
import java.util.UUID;

public interface CounterAccess {

    Collection<UUID> getCounters();
    long getCounterValue(UUID id);
}
