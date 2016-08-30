package io.datanerds.rappelkiste.service;

import io.datanerds.rappelkiste.api.CounterResource;
import io.datanerds.rappelkiste.api.PatchOperation;
import io.datanerds.rappelkiste.service.counter.CounterModule;
import io.datanerds.rappelkiste.service.events.AddValueToCounter;
import io.datanerds.rappelkiste.service.events.CreateCounter;

import javax.ws.rs.core.Response;
import java.util.UUID;

public class CounterService implements CounterResource {


    @Override
    public Response getCounters() {
        return Response.ok(CounterModule.provideCounterAccess().getCounters()).build();
    }

    @Override
    public Response createCounter() {
        UUID id = UUID.randomUUID();
        CounterModule.provideEventQueue().addEvent(new CreateCounter(id));
        return Response.ok(id).build();
    }

    @Override
    public Response getCounter(UUID id) {
        return Response.ok(CounterModule.provideCounterAccess().getCounterValue(id)).build();
    }

    @Override
    public Response patchCounter(UUID id, PatchOperation operation) {
        CounterModule.provideEventQueue().addEvent(new AddValueToCounter(id, operation.value));
        return Response.ok().build();
    }
}
