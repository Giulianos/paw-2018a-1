package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.ImageService;
import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.webapp.dto.ImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/images")
@Controller
public class ImagesController {

  @Autowired
  private ImageService imageService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{id}")
  public Response retrieve(@PathParam("id") final Long id) {
    if (id == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

    Optional<Image> image = imageService.findById(id);

    if(!image.isPresent()) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    // Set cache control for the resource
    CacheControl cc = new CacheControl();
    cc.setMaxAge(31536000); // id is unique for each image (aggressive caching)
    cc.setPrivate(false);

    return Response.ok(new ImageDTO(image.get())).cacheControl(cc).build();
  }

}
