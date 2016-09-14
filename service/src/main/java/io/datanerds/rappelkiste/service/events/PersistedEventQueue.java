package io.datanerds.rappelkiste.service.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.tape.QueueFile;
import io.datanerds.rappelkiste.service.exception.RappelkisteException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PersistedEventQueue implements EventQueue {

    private static final int WAIT_ON_EMPTY_LIST_IN_MILLIS = 200;

    private final QueueFile queueFile;
    private final ObjectMapper mapper;
    private final List<EventHandler> handlers;
    private final Consumer consumer;

    public PersistedEventQueue() {
        this.mapper = new ObjectMapper();
        this.handlers = new CopyOnWriteArrayList<>();
        this.consumer = new Consumer();

        try {
            Path folder = Files.createTempDirectory("event-queue");
            File file = new File(folder.toFile(), "event.queue");
            this.queueFile = new QueueFile(file);
        } catch (IOException ex) {
            throw new RappelkisteException("Could not initiate EventQueue", ex);
        }

        new Thread(consumer).start();
    }

    @Override
    public void addEvent(CounterEvent event) {
        try {
            queueFile.add(serialize(event));
        } catch (IOException ex) {
            throw new RappelkisteException("Could not write bytes to tape.", ex);
        }
    }

    @Override
    public void registerHandler(EventHandler handler) {
        handlers.add(handler);
    }

    @Override
    public void unregisterHandler(EventHandler handler) {
        handlers.remove(handler);
    }

    @Override
    public void shutdown() {
        consumer.stop();
    }

    private byte[] serialize(CounterEvent event) {
        try {
            return mapper.writeValueAsBytes(event);
        } catch (JsonProcessingException ex) {
            throw new RappelkisteException("Could not serialize event.", ex);
        }
    }

    private CounterEvent deserialize(byte[] bytes) {
        try {
            return mapper.readValue(bytes, CounterEvent.class);
        } catch (IOException ex) {
            throw new RappelkisteException("Could not deserialize event", ex);
        }
    }

    class Consumer implements Runnable {

        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                if (queueFile.isEmpty()) {
                    sleep();
                    continue;
                }
                CounterEvent event = peek();
                handlers.forEach(handler -> handler.handle(event));
                remove();
            }
        }

        public void stop() {
            running = false;
        }

        private CounterEvent peek() {
            try {
                return deserialize(queueFile.peek());
            } catch (IOException ex) {
                throw new RappelkisteException("Could not read eldest event from tape", ex);
            }
        }

        private void remove() {
            try {
                queueFile.remove();
            } catch (IOException ex) {
                throw new RappelkisteException("Could not remove eldest event from tape", ex);
            }
        }

        private void sleep() {
            try {
                Thread.sleep(WAIT_ON_EMPTY_LIST_IN_MILLIS);
            } catch (InterruptedException ex) {
                throw new RappelkisteException("Could not sleep consuming thread", ex);
            }
        }
    }
}
