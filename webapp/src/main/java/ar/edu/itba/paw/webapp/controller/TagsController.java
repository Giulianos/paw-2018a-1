package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.TagService;
import ar.edu.itba.paw.model.Tag;
import ar.edu.itba.paw.webapp.dto.TagListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tags")
@Controller
public class TagsController {
  @Autowired
  TagService tagService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response list(@QueryParam("keyword") final String keyword, @QueryParam("limit") final Integer limit) {
    List<Tag> tagList = tagService.list(keyword, limit);

    return Response.ok(new TagListDTO(tagList)).build();
  }
}
