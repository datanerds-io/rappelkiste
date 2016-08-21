package io.datanerds.rappelkiste.service;

import io.datanerds.rappelkiste.api.CounterResource;
import io.datanerds.rappelkiste.service.events.CreateCounter;
import io.datanerds.rappelkiste.service.events.EventQueue;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.UUID;

public class CounterService implements CounterResource {

    private EventQueue queue;
    private CounterAccess counterAccess;

    @Override
    public Response getCounters() {
        return Response.ok(counterAccess.getCounters()).build();
    }

    @Override
    public Response createCounter() {
        UUID id = UUID.randomUUID();
        queue.add(new CreateCounter(id));
        return Response.ok(id).build();
    }

    @Override
    public Response getCounter(UUID id) {
        return Response.ok(counterAccess.getCounterValue(id)).build();
    }

    @Override
    public Response patchCounter(@PathParam("id") UUID id) {
        return Response.ok(id).build();
    }
}
