package io.datanerds.rappelkiste.service;

import io.datanerds.rappelkiste.api.PingResource;

import javax.ws.rs.core.Response;

public class PingService implements PingResource {

    @Override
    public Response ping() {
        return Response.ok("pong").build();
    }
}
