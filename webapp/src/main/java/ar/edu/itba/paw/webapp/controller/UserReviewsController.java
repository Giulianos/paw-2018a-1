package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exception.EntityNotFoundException;
import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.interfaces.service.ReviewService;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.webapp.dto.ReviewListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users/{userId}/reviews")
@Controller
public class UserReviewsController {

  @PathParam("userId")
  private Long userId;

  @Autowired
  private ReviewService reviewService;

  @GET
  public Response list() {
    if(userId == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

    try {
      List<Review> userReviews = reviewService.getUserReviews(userId);

      return Response.ok(new ReviewListDTO(userReviews)).build();
    } catch(UnauthorizedAccessException e) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    } catch (EntityNotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } catch(Exception e) {
      return Response.serverError().build();
    }
  }

}
