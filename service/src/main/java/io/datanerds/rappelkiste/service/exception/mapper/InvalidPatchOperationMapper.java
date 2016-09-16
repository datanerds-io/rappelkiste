package io.datanerds.rappelkiste.service.exception.mapper;

import io.datanerds.rappelkiste.api.patch.InvalidPatchOperationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
public class InvalidPatchOperationMapper implements ExceptionMapper<InvalidPatchOperationException> {

    @Override
    public Response toResponse(InvalidPatchOperationException exception) {
        return Response.status(BAD_REQUEST).type(TEXT_PLAIN).entity(exception.getMessage()).build();
    }
}
