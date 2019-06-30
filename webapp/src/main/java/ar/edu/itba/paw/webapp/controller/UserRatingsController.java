package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exception.EntityNotFoundException;
import ar.edu.itba.paw.interfaces.service.ReviewService;
import ar.edu.itba.paw.webapp.dto.RatingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/users/{userId}/ratings")
@Controller
public class UserRatingsController {
  @PathParam("userId")
  private Long userId;

  @Autowired
  private ReviewService reviewService;

  @GET
  public Response getRating() {

    if(userId == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

    try {
      Integer userRating = reviewService.getUserRating(userId);

      return Response.ok(new RatingDTO(userRating)).build();
    } catch (EntityNotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } catch (Exception e) {
      return Response.serverError().build();
    }
  }
}
