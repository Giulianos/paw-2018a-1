package ar.edu.itba.paw.services.listener;

import ar.edu.itba.paw.interfaces.dao.NotificationDao;
import ar.edu.itba.paw.model.NotificationType;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.events.PublicationPurchasedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PublicationPurchasedListener implements ApplicationListener<PublicationPurchasedEvent> {

  @Autowired
  private NotificationDao notificationDao;

  @Override
  public void onApplicationEvent(PublicationPurchasedEvent publicationPurchasedEvent) {
    Publication publication = publicationPurchasedEvent.getSource();

    // Notify orderers of the event
    publication.getOrders()
        .stream()
        .filter(o -> !o.getOrderer().equals(publication.getSupervisor()))
        .forEach(o -> {
          notificationDao.create(o.getOrderer(), NotificationType.ORDER_PURCHASED, publication, o);
        });
  }
}
