package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.dto.UserDTO;
import ar.edu.itba.paw.webapp.dto.UserRegisterDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("users")
@Component
public class UsersController {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private UserService userService;

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(final UserRegisterDTO user) {
        try {
            User createdUser = userService.create(
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword()
            );

            return Response.ok(new UserDTO(createdUser)).build();
        } catch(Exception e) {
            // TODO: create ErrorDTO with an error message and return that
            return Response.serverError().build();
        }
    }

}
