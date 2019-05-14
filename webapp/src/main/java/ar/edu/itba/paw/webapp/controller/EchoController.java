package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.dto.SampleEchoDTO;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("echo")
@Component
public class EchoController {

  @Context
  private UriInfo uriInfo;

  @GET
  @Path("/")
  @Produces(MediaType.TEXT_PLAIN)
  public String helpMessage() {
    return "POST a message to this path and I will echo it!";
  }

  @POST
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public SampleEchoDTO echo(final SampleEchoDTO message) {
    return new SampleEchoDTO(message.getMessage());
  }

  @POST
  @Path("/upper")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public SampleEchoDTO upper(final SampleEchoDTO message) {
    return new SampleEchoDTO(message.getMessage().toUpperCase());
  }
}
