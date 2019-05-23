package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.dto.ErrorDTO;
import ar.edu.itba.paw.webapp.dto.constraints.ConstraintViolationsDTO;
import ar.edu.itba.paw.webapp.dto.UserDTO;
import ar.edu.itba.paw.webapp.dto.UserRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.*;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Set;

@Path("users")
@Controller
public class UsersController {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private UserService userService;
    @Autowired
    private Validator validator;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(@Valid UserRegisterDTO user) {

        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(user);
        if(!violations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ConstraintViolationsDTO(violations)).build();
        }

        try {
            User createdUser = userService.create(
                    user.getName(),
                    user.getEmail(),
                    user.getPassword()
            );

            return Response.ok(new UserDTO(createdUser)).build();
        } catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorDTO("User could no be created")).build();
        }
    }

}
