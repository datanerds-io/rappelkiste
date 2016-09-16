package io.datanerds.rappelkiste.service.exception.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
public class JsonProcessingMapper implements ExceptionMapper<JsonProcessingException> {

    @Override
    public Response toResponse(JsonProcessingException exception) {
        return Response.status(BAD_REQUEST)
                .type(TEXT_PLAIN)
                .entity(String.format("Could not process given JSON due to %s", exception.getCause().getMessage()))
                .build();
    }
}
