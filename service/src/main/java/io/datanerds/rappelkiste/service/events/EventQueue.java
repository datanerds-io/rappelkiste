package io.datanerds.rappelkiste.service.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.tape.QueueFile;
import io.datanerds.rappelkiste.service.RappelkisteException;

import java.io.File;
import java.io.IOException;

/**
 * Thread safe event queue backed on tape
 */
public class EventQueue {

    private final EventHandler handler;
    private final QueueFile queueFile;
    private final ObjectMapper mapper;

    public EventQueue(EventHandler handler, File file) throws IOException {
        this.handler = handler;
        this.mapper = new ObjectMapper();
        this.queueFile = new QueueFile(file);
    }

    public void add(CounterEvent event) {
        try {
            queueFile.add(serialize(event));
        } catch (IOException ex) {
            throw new RappelkisteException("Could not write bytes to tape.", ex);
        }
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

        @Override
        public void run() {
            if (queueFile.isEmpty()) {

            }
            CounterEvent event = peek();
            handler.handle(event);
            remove();
        }
    }


    public interface EventHandler {
        <T extends CounterEvent> void handle(T event);
    }

}
