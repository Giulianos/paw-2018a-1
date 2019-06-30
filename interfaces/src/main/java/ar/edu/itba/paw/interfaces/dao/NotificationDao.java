package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.*;

import java.util.List;

public interface NotificationDao {
  /**
   * Creates a new notification
   * @param type
   * @param relatedPublication
   * @param relatedOrder
   * @return The created notification
   */
  public Notification create(final User user, final NotificationType type, final Publication relatedPublication, final Order relatedOrder);

  /**
   * Retrieves unseen notifications of user
   * @param userId
   * @return the list of notifications
   */
  public List<Notification> getUnseen(final Long userId);

  /**
   * Retrieves the latest notification for user
   * @param userId
   * @param limit
   * @return the list of notifications
   */
  public List<Notification> getLatest(final Long userId, final Integer limit);

  /**
   * Updated the passed notification
   * @param notification
   */
  public void update(final Notification notification);
}
