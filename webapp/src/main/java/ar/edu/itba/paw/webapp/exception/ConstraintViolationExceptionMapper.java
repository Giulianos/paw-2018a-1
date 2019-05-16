package ar.edu.itba.paw.webapp.exception;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
  @Override
  public Response toResponse(ConstraintViolationException e) {
    final StringBuilder responseEntity = new StringBuilder();

    responseEntity.append("{ \"status\"=\"constraint errors\" }");

    return Response.status(Response.Status.BAD_REQUEST).entity(responseEntity.toString()).build();
  }
}
