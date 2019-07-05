package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exception.EntityNotFoundException;
import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.interfaces.service.PublicationService;
import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.webapp.dto.ImageDTO;
import ar.edu.itba.paw.webapp.utils.URLResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Optional;

@Path("/publications/{publicationId}/images")
@Controller
public class PublicationImagesController {

  @Autowired
  private PublicationService publicationService;

  @PathParam("publicationId")
  private Long publiationId;

  @POST
  @Consumes(MediaType.TEXT_PLAIN)
  public Response add(final String base64Image) {

    if (base64Image == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

    final String loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

    try {
      Optional<Image> image = publicationService.addImage(loggedUserEmail, publiationId, base64Image);

      return Response
          .status(Response.Status.CREATED)
          .entity(new ImageDTO(image.get()))
          .location(new URI(URLResolver.getFullURL("/images/") + image.get().getId()))
          .build();
    } catch (UnauthorizedAccessException e) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    } catch (EntityNotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } catch (IllegalStateException e) {
      return Response.status(Response.Status.CONFLICT).build();
    } catch (Exception e) {
      e.printStackTrace();
      return Response.serverError().build();
    }
  }

}
