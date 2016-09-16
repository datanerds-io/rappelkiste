package io.datanerds.rappelkiste.api;

import io.datanerds.rappelkiste.api.patch.PATCH;
import io.datanerds.rappelkiste.api.patch.PatchOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/v1")
public interface CounterResource {
    @POST
    @Path("/counter")
    @Produces(MediaType.APPLICATION_JSON)
    Response createCounter();

    @GET
    @Path("/counter/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getCounter(@PathParam("id") UUID id);

    @PATCH
    @Path("/counter/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response patchCounter(@PathParam("id") UUID id, PatchOperation operation);

    @GET
    @Path("/counters")
    @Produces(MediaType.APPLICATION_JSON)
    Response getCounters();
}
