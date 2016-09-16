package io.datanerds.rappelkiste.service;

import io.datanerds.rappelkiste.api.CounterResource;
import io.datanerds.rappelkiste.api.patch.PatchOperation;
import io.datanerds.rappelkiste.service.counter.CounterModule;
import io.datanerds.rappelkiste.service.events.AddValueToCounter;
import io.datanerds.rappelkiste.service.events.CreateCounter;

import javax.ws.rs.core.Response;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

public class CounterService implements CounterResource {

    @Override
    public Response getCounters() {
        return Response.ok(CounterModule.provideCounterAccess().getCounters()).build();
    }

    @Override
    public Response createCounter() {
        UUID id = UUID.randomUUID();
        CounterModule.provideEventQueue().addEvent(new CreateCounter(id));
        return Response.status(CREATED).entity(id).build();
    }

    @Override
    public Response getCounter(UUID id) {
        return Response.ok(CounterModule.provideCounterAccess().getCounterValue(id)).build();
    }

    @Override
    public Response patchCounter(UUID id, PatchOperation operation) {
        CounterModule.provideEventQueue().addEvent(new AddValueToCounter(id, operation.value));
        return Response.status(NO_CONTENT).build();
    }
}
