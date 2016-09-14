package io.datanerds.rappelkiste.service;

import io.datanerds.rappelkiste.service.exception.CounterNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CounterNotFoundMapper implements ExceptionMapper<CounterNotFoundException> {

    @Override
    public Response toResponse(CounterNotFoundException exception) {
        return Response.status(404).entity(exception.getMessage()).type("text/plain").build();
    }
}
