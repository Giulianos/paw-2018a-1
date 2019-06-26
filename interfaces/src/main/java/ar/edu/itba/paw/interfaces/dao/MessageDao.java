package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Message;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.User;

import java.util.List;

public interface MessageDao {

  /** Create a message
   *
   * @param order
   * @param from
   * @param to
   * @param message
   * @return The created message
   */
  public Message createMessage(final Order order, final User from, final User to, final String message);

  /**
   * Mark all messages as read
   * @param messages
   */
  public void markRead(final List<Message> messages);

  /**
   * Get unread messages to receiver in order
   * @param order
   * @param receiver
   * @return The list of unread messages
   */
  public List<Message> getUnread(final Order order, final User receiver);
}
