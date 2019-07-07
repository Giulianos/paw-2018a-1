package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exception.EntityNotFoundException;
import ar.edu.itba.paw.interfaces.exception.PublicationFulfilledException;
import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.interfaces.service.OrderService;
import ar.edu.itba.paw.interfaces.service.PublicationService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.compositepks.OrderId;
import ar.edu.itba.paw.webapp.dto.ErrorDTO;
import ar.edu.itba.paw.webapp.dto.OrderPageDTO;
import ar.edu.itba.paw.webapp.dto.PublicationsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("users/{userId}/publications")
@Controller
public class UserPublicationsController {

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private UserService userService;

    @PathParam("userId")
    private Long userId;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@QueryParam("page") final Integer page, @QueryParam("pageSize") final Integer pageSize) {
        /*if(page == null || pageSize == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }*/

        try {
            List<Publication> userPublications = publicationService.userPublications();

            PublicationsDTO publicationsDTO = new PublicationsDTO(userPublications);

            return Response.ok(publicationsDTO).build();
        } catch (UnauthorizedAccessException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
