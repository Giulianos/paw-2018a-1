package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Notification;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationListDTO {
  private List<NotificationDTO> notifications;

  public NotificationListDTO() {
    // Needed by JAX-RS
  }

  public NotificationListDTO(final List<Notification> notifications) {
    this.notifications = notifications.stream().map(NotificationDTO::new).collect(Collectors.toList());
  }

  public List<NotificationDTO> getNotifications() {
    return notifications;
  }

  public void setNotifications(List<NotificationDTO> notifications) {
    this.notifications = notifications;
  }
}
