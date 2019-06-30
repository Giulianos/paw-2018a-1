package ar.edu.itba.paw.services.listener;

import ar.edu.itba.paw.interfaces.dao.NotificationDao;
import ar.edu.itba.paw.interfaces.dao.PublicationDao;
import ar.edu.itba.paw.interfaces.service.EmailService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.events.PublicationFulfilledEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class PublicationFulfilledListener implements ApplicationListener<PublicationFulfilledEvent> {

    @Autowired
    private PublicationDao publicationDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private NotificationDao notificationDao;

    @Override
    @Transactional
    public void onApplicationEvent(PublicationFulfilledEvent event) {
        final Publication fulfilledPublication = (Publication) event.getSource();

        /* Set publication as fulfilled */
        fulfilledPublication.setState(PublicationState.FULFILLED);
        publicationDao.update(fulfilledPublication);

        /* Get updated publication */
        Optional<Publication> updatedPublication = publicationDao.findById(fulfilledPublication.getId());

        if(!updatedPublication.isPresent()) {
            throw new IllegalStateException();
        }


        /* TODO: Notify orderers (this should also be done conditionally) */
        updatedPublication
                .get()
                .getOrders()
                .stream()
                // Remove supervisor form orderers (it will be notified later)
                .filter(o -> !o.getOrderer().equals(updatedPublication.get().getSupervisor()))
                .forEach(o -> {
                    emailService.notifyOrdererPublicationFulfillment(o.getOrderer(), updatedPublication.get());
                    notificationDao.create(o.getOrderer(), NotificationType.PUBLICATION_FULFILLED, updatedPublication.get(), o);
                });

        /* TODO: Notify supervisor (this should be done conditionally based on user notification preferences) */
        emailService.notifySupervisorPublicationFulfillment(updatedPublication.get());
        notificationDao.create(updatedPublication.get().getSupervisor(), NotificationType.PUBLICATION_FULFILLED, updatedPublication.get(), null);
    }
}
