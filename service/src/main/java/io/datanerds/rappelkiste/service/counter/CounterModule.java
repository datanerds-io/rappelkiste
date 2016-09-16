package io.datanerds.rappelkiste.service.counter;

import io.datanerds.rappelkiste.service.events.EventQueue;
import io.datanerds.rappelkiste.service.events.PersistedEventQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CounterModule {

    private final InMemoryCounterManager counterManager;
    private final EventQueue eventQueue;

    private static final CounterModule INSTANCE = new CounterModule();
    private static final Logger logger = LoggerFactory.getLogger(CounterModule.class);

    private CounterModule() {
        counterManager = new InMemoryCounterManager();
        eventQueue = new PersistedEventQueue();
        EventQueue.EventHandler eventHandler = new LazyEventHandler(counterManager);
        eventQueue.registerHandler(eventHandler);
    }

    public static CounterManager provideCounterAccess() {
        return INSTANCE.counterManager;
    }

    public static EventQueue provideEventQueue() {
        return INSTANCE.eventQueue;
    }

    public static void shutdown() {
        logger.info("Shutting down counter module.");
        INSTANCE.eventQueue.shutdown();
    }
}
