package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Notification;

import java.util.List;

public interface NotificationService {
  /**
   * Retrieves all unseen notifications,
   * if those are less than 10, the remaining
   * are seen notifications
   * @return the list of notifications
   */
  public List<Notification> getLatest();

  /**
   * Marks all notifications as seen
   */
  public void markAllSeen();
}
