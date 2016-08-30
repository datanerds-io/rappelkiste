package io.datanerds.rappelkiste.service.events;

public interface EventQueue {

    void addEvent(CounterEvent event);
    void registerHandler(EventHandler handler);
    void unregisterHandler(EventHandler handler);
    void shutdown();

    interface EventHandler {
        <T extends CounterEvent> void handle(T event);
    }

}
