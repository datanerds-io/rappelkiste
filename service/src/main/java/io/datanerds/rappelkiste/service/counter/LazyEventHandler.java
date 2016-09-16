package io.datanerds.rappelkiste.service.counter;

import io.datanerds.rappelkiste.service.exception.RappelkisteException;
import io.datanerds.rappelkiste.service.events.AddValueToCounter;
import io.datanerds.rappelkiste.service.events.CounterEvent;
import io.datanerds.rappelkiste.service.events.CreateCounter;
import io.datanerds.rappelkiste.service.events.EventQueue;

import static java.lang.Math.random;
import static java.lang.Math.round;

public class LazyEventHandler implements EventQueue.EventHandler {

    public static int MAX_SLEEP_TIME_IN_MILLIS = 2000;

    private final CounterManager counterManager;

    public LazyEventHandler(CounterManager counterManager) {
        this.counterManager = counterManager;
    }

    @Override
    public <T extends CounterEvent> void handle(T event) {
        sleep();

        if (event instanceof CreateCounter) {
            createCounter((CreateCounter) event);
        } else if (event instanceof AddValueToCounter) {
            addValue((AddValueToCounter) event);
        }
    }

    private void createCounter(CreateCounter create) {
        counterManager.createCounter(create.counterId);
    }

    private void addValue(AddValueToCounter add) {
        counterManager.addValue(add.counterId, add.value);
    }

    private void sleep() {
        try {
            Thread.sleep(round(MAX_SLEEP_TIME_IN_MILLIS * random()));
        } catch (InterruptedException ex) {
            throw new RappelkisteException("Could not sleep consuming thread", ex);
        }
    }
}
