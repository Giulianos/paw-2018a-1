package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.NotificationType;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;

public interface NotificationDao {
  /**
   * Creates a new notification
   * @param type
   * @param relatedPublication
   * @param relatedOrder
   * @return The created notification
   */
  public Notification create(final NotificationType type, final Publication relatedPublication, final Order relatedOrder);

  /**
   * Updated the passed notification
   * @param notification
   */
  public void update(final Notification notification);
}
