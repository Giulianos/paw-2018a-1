package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exception.EntityNotFoundException;
import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.interfaces.service.PublicationService;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.webapp.dto.ErrorDTO;
import ar.edu.itba.paw.webapp.dto.PublicationDTO;
import ar.edu.itba.paw.webapp.dto.PublicationNewDTO;
import ar.edu.itba.paw.webapp.dto.PublicationStateDTO;
import ar.edu.itba.paw.webapp.dto.constraints.ConstraintViolationsDTO;
import ar.edu.itba.paw.webapp.httpmethods.PATCH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Optional;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response retreive(@PathParam("id") Long id) {
        if(id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO("Invalid or undefined publication id")).build();
        }

        Optional<Publication> publicationOptional = publicationService.findById(id);

        if(!publicationOptional.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorDTO("Publication not found")).build();
        }

        return Response.ok(new PublicationDTO(publicationOptional.get())).build();
    }

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
                    publication.getDetailedDescription(),
                    publication.getTags()
            );

            return Response.ok(new PublicationDTO(createdPublication)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorDTO("Publication could no be created")).build();
        }
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response markPurchased(@Valid PublicationStateDTO publicationState, @PathParam("id") final Long publicationId) {
        Set<ConstraintViolation<PublicationStateDTO>> violations = validator.validate(publicationState);
        if(!violations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ConstraintViolationsDTO(violations)).build();
        }

        try {
            publicationService.markAsPurchased(publicationId);

            return Response.accepted().build();
        } catch(UnauthorizedAccessException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch(EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch(IllegalStateException e) {
            return Response.status(Response.Status.CONFLICT).build();
        } catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
