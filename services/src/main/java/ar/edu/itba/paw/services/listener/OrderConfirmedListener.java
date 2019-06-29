package ar.edu.itba.paw.services.listener;

import ar.edu.itba.paw.interfaces.dao.PublicationDao;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.PublicationState;
import ar.edu.itba.paw.model.events.OrderConfirmedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderConfirmedListener implements ApplicationListener<OrderConfirmedEvent> {

  @Autowired
  private PublicationDao publicationDao;

  @Override
  @Transactional
  public void onApplicationEvent(OrderConfirmedEvent event) {
    Order confirmedOrder = event.getSource();

    Publication publication = confirmedOrder.getPublication();

    // Check if all orders are confirmed
    if(publication.getOrders().parallelStream().allMatch(Order::getPurchaseAccepted)) {
      // Mark publication as finalized
      publication.setState(PublicationState.FINALIZED);
      publicationDao.update(publication);
    } else {
      System.out.println("Not all orders had been confirmed!");
    }
  }
}
