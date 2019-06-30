package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exception.EntityNotFoundException;
import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.interfaces.service.OrderService;
import ar.edu.itba.paw.interfaces.service.ReviewService;
import ar.edu.itba.paw.model.Message;
import ar.edu.itba.paw.model.compositepks.OrderId;
import ar.edu.itba.paw.webapp.dto.ErrorDTO;
import ar.edu.itba.paw.webapp.dto.MessageListDTO;
import ar.edu.itba.paw.webapp.dto.MessageNewDTO;
import ar.edu.itba.paw.webapp.dto.ReviewDTO;
import ar.edu.itba.paw.webapp.dto.constraints.ConstraintViolationsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@Path("users/{userId}/orders/{publicationId}/reviews")
@Controller
public class UserOrderReviewsController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private Validator validator;

    @PathParam("userId")
    private Long userId;

    @PathParam("publicationId")
    private Long publicationId;

    @POST
    public Response create(@Valid ReviewDTO review) {
        if(publicationId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Set<ConstraintViolation<ReviewDTO>> violations = validator.validate(review);
        if (!violations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ConstraintViolationsDTO(violations)).build();
        }

        OrderId orderId = new OrderId(userId, publicationId);

        try {
           reviewService.reviewOrder(orderId, review.getComment(), review.getRating());

            return Response.status(Response.Status.CREATED).build();
        } catch(EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch(UnauthorizedAccessException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch(IllegalStateException e) {
            return Response.status(Response.Status.CONFLICT).entity(new ErrorDTO(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("EXCEPTION").build();
        }
    }

}
