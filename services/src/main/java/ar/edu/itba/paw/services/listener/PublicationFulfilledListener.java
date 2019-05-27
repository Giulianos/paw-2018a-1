package ar.edu.itba.paw.services.listener;

import ar.edu.itba.paw.interfaces.dao.PublicationDao;
import ar.edu.itba.paw.interfaces.service.EmailService;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;
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

    @Override
    @Transactional
    public void onApplicationEvent(PublicationFulfilledEvent event) {
        System.out.println("Publication fulfilled");
        final Publication fulfilledPublication = (Publication) event.getSource();

        /* Set publication as fulfilled */
        fulfilledPublication.setFulfilled(true);
        publicationDao.update(fulfilledPublication);

        /* Get updated publication */
        Optional<Publication> updatedPublication = publicationDao.findById(fulfilledPublication.getId());

        if(!updatedPublication.isPresent()) {
            throw new IllegalStateException();
        }


        /* Notify orderers (this should also be done conditionally) */
        updatedPublication
                .get()
                .getOrders()
                .stream()
                .map(Order::getOrderer)
                // Remove supervisor form orderers (it will be notified later)
                .filter(u -> !u.equals(updatedPublication.get().getSupervisor()))
                .forEach((User u) -> emailService.notifyOrdererPublicationFulfillment(u, updatedPublication.get()));

        /* Notify supervisor (this should be done conditionally based on user notification preferences) */
        emailService.notifySupervisorPublicationFulfillment(updatedPublication.get());
    }
}
