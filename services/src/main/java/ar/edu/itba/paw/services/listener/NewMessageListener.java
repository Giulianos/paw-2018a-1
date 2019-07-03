package ar.edu.itba.paw.services.listener;

import ar.edu.itba.paw.interfaces.dao.NotificationDao;
import ar.edu.itba.paw.model.Message;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.NotificationType;
import ar.edu.itba.paw.model.events.NewMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class NewMessageListener implements ApplicationListener<NewMessageEvent> {
  @Autowired
  private NotificationDao notificationDao;

  @Override
  @Transactional
  public void onApplicationEvent(NewMessageEvent event) {
    Message message = event.getSource();

    // Check if we already have an unseen notification
    // for a message of the same order
    List<Notification> unseen = notificationDao.getUnseen(message.getReceiver().getId());
    if(!notificationForMessageExists(message, unseen)) {
      // Create a notification
      notificationDao.create(message.getReceiver(), NotificationType.NEW_MESSAGES, null, message.getOrder(), message);
    }
  }

  private Boolean notificationForMessageExists(final Message message, final List<Notification> unseenNotifications) {
    return unseenNotifications
        .parallelStream()
        .anyMatch(n -> n.getType().equals(NotificationType.NEW_MESSAGES) &&  n.getRelatedOrder().equals(message.getOrder()));
  }
}
