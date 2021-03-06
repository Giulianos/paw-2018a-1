package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.NotificationService;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.webapp.dto.NotificationListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users/{userId}/notifications")
@Controller
public class UserNotificationsController {

  @Autowired
  private NotificationService notificationService;

  @PathParam("userId")
  private Long userId;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response unseen() {
    try {
      List<Notification> notifications = notificationService.getLatest();

      return Response.ok(new NotificationListDTO(notifications)).build();
    } catch(IllegalStateException e) {
      return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
    }
  }

  @DELETE
  public Response markSeen() {
    try {
      notificationService.markAllSeen();

      return Response.status(Response.Status.ACCEPTED).build();
    } catch(IllegalStateException e) {
      return Response.status(Response.Status.CONFLICT).build();
    }
  }

}
