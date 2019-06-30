package ar.edu.itba.paw.services.listener;

import ar.edu.itba.paw.interfaces.dao.NotificationDao;
import ar.edu.itba.paw.model.NotificationType;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.events.SupervisorLeftEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SupervisorLeftListener implements ApplicationListener<SupervisorLeftEvent> {

  @Autowired
  private NotificationDao notificationDao;

  @Override
  public void onApplicationEvent(SupervisorLeftEvent supervisorLeftEvent) {
    Publication publication = supervisorLeftEvent.getSource();

    // Notify orderers that the supervisor left
    publication.getOrders().parallelStream().forEach(o -> {
     notificationDao.create(o.getOrderer(), NotificationType.PUBLICATION_ORPHAN, publication, o);
    });
  }
}
