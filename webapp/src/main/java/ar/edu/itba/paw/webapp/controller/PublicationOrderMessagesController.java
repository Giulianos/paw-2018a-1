package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exception.EntityNotFoundException;
import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.interfaces.service.OrderService;
import ar.edu.itba.paw.interfaces.service.PublicationService;
import ar.edu.itba.paw.model.Message;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.compositepks.OrderId;
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

@Path("publications/{id}/orders/{userid}/messages")
@Controller
public class PublicationOrderMessagesController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private Validator validator;

  @Autowired
  private PublicationService publicationService;

  @PathParam("id")
  private Long publicationId;

  @PathParam("userid")
  private Long userId;

  @GET
  public Response list() {
    if(publicationId == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

    OrderId orderId = new OrderId(userId, publicationId);

    try {
      List<Message> orderMessages = orderService.getOrderMessagesById(orderId);

      return Response.ok(new MessageListDTO(orderMessages)).build();
    } catch(EntityNotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } catch(UnauthorizedAccessException e) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  @POST
  public Response create(@Valid MessageNewDTO messageNew) {
    if(publicationId == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

    Set<ConstraintViolation<MessageNewDTO>> violations = validator.validate(messageNew);
    if (!violations.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST).entity(new ConstraintViolationsDTO(violations)).build();
    }

    OrderId orderId = new OrderId(userId, publicationId);

    try {
      Message sentMessage = orderService.sendMessage(orderId, messageNew.getMessage());

      return Response.status(Response.Status.CREATED).entity(new MessageDTO(sentMessage)).build();
    } catch(EntityNotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } catch(UnauthorizedAccessException e) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("EXCEPTION").build();
    }
  }

  @GET
  @Path("/unseen")
  public Response listUnseen() {
    if(publicationId == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

    OrderId orderId = new OrderId(userId, publicationId);

    try {
      List<Message> unreadMessages = orderService.getOrderUnseenMessages(orderId);

      return Response.ok(new MessageListDTO(unreadMessages)).build();
    } catch(EntityNotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } catch(UnauthorizedAccessException e) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
  }

  @PUT
  @Path("/unseen")
  public Response markRead() {
    if(publicationId == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

    OrderId orderId = new OrderId(userId, publicationId);

    try {
      orderService.markMessagesAsSeen(orderId);

      return Response.status(Response.Status.ACCEPTED).build();
    } catch(EntityNotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } catch(UnauthorizedAccessException e) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
  }

}
