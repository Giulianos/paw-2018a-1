package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.NotificationDao;
import ar.edu.itba.paw.interfaces.service.NotificationService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Primary
@Service
public class NotificationServiceImpl implements NotificationService {

  @Autowired
  private NotificationDao notificationDao;
  @Autowired
  private UserService userService;

  @Override
  @Transactional
  public List<Notification> getLatest() {
    final String loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    final Optional<User> loggedUser = userService.findByEmail(loggedUserEmail);

    if (!loggedUser.isPresent()) {
      throw new IllegalStateException("User is logged but it doesn't exist in DB");
    }

    List<Notification> notifications = notificationDao.getUnseen(loggedUser.get().getId());

    return notifications;
  }

  @Override
  @Transactional
  public void markAllSeen() {
    List<Notification> unseenNotifications = getLatest();
    unseenNotifications.forEach(
        notification -> {
          notification.setSeen(true);
          notificationDao.update(notification);
        });
  }
}
