package io.datanerds.rappelkiste.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Provider
public class CounterNotFoundMapper implements ExceptionMapper<CounterNotFoundException> {

    @Override
    public Response toResponse(CounterNotFoundException exception) {
        return Response.status(NOT_FOUND).entity(exception.getMessage()).type("text/plain").build();
    }
}
