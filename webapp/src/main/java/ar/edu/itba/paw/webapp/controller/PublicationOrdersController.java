package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.interfaces.service.OrderService;
import ar.edu.itba.paw.interfaces.service.PublicationService;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.webapp.dto.*;
import ar.edu.itba.paw.webapp.dto.constraints.ConstraintViolationsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Path("publications/{id}/orders")
@Controller
public class PublicationOrdersController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private Validator validator;

  @Autowired
  private PublicationService publicationService;

  @PathParam("id")
  private Long publicationId;

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response create(@Valid OrderNewDTO orderNew) {

    Set<ConstraintViolation<OrderNewDTO>> violations = validator.validate(orderNew);
    if (!violations.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST).entity(new ConstraintViolationsDTO(violations)).build();
    }

    try {
      Optional<Publication> publication = publicationService.findById(publicationId);

      if(!publication.isPresent()) {
        return Response.status(Response.Status.NOT_FOUND).entity(new ErrorDTO("Publication not found")).build();
      }

      Order createdOrder = orderService.create(
          publication.get(),
          orderNew.getQuantity()
      );

      return Response.ok(new OrderDTO(createdOrder)).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorDTO("Order could no be created")).build();
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response list(@QueryParam("page") final Integer page, @QueryParam("pageSize") final Integer pageSize) {
    if(page == null || pageSize == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

    Optional<Publication> publication = publicationService.findById(publicationId);

    if(!publication.isPresent()) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    try {
      List<Order> publicationOrders = orderService.publicationOrders(publication.get(), page, pageSize);
      Long publicationOrdersQuantity = orderService.publicationOrdersQuantity(publication.get());

      OrderPageDTO orderPage = new OrderPageDTO(
              publicationOrders,
              new Long(page),
              publicationOrdersQuantity,
              new Long(pageSize)
      );

      return Response.ok(orderPage).build();

    } catch(UnauthorizedAccessException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } catch(Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

}
