package io.datanerds.rappelkiste.service;

import io.datanerds.rappelkiste.api.CounterResource;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.UUID;

public class CounterService implements CounterResource {
    @Override
    public Response getCounters() {
        return Response.ok("test").build();
    }

    @Override
    public Response createCounter() {
        return Response.ok("test").build();
    }

    @Override
    public Response getCounter(UUID id) {
        return Response.ok(id).build();
    }

    @Override
    public Response patchCounter(@PathParam("id") UUID id) {
        return Response.ok(id).build();
    }
}
