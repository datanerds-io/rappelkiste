package io.datanerds.rappelkiste.service.exception;

import io.datanerds.rappelkiste.api.patch.InvalidPatchOperationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class InvalidPatchOperationMapper implements ExceptionMapper<InvalidPatchOperationException> {

    @Override
    public Response toResponse(InvalidPatchOperationException exception) {
        return Response.status(BAD_REQUEST).entity(exception.getMessage()).type("text/plain").build();
    }
}
