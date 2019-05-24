package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.PublicationService;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.webapp.dto.ErrorDTO;
import ar.edu.itba.paw.webapp.dto.PublicationDTO;
import ar.edu.itba.paw.webapp.dto.PublicationNewDTO;
import ar.edu.itba.paw.webapp.dto.constraints.ConstraintViolationsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Set;

@Path("publications")
@Controller
public class PublicationsController {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private Validator validator;

    @Autowired
    private PublicationService publicationService;


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid PublicationNewDTO publication) {

        Set<ConstraintViolation<PublicationNewDTO>> violations = validator.validate(publication);
        if (!violations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ConstraintViolationsDTO(violations)).build();
        }

        try {
            Publication createdPublication = publicationService.create(
                    publication.getDescription(),
                    publication.getUnitPrice(),
                    publication.getQuantity(),
                    publication.getDetailedDescription()
            );

            System.out.println(createdPublication);

            return Response.ok(new PublicationDTO(createdPublication)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorDTO("Publication could no be created")).build();
        }
    }
}
