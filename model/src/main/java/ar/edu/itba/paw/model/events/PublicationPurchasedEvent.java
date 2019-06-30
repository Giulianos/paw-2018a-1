package ar.edu.itba.paw.model.events;

import ar.edu.itba.paw.model.Publication;
import org.springframework.context.ApplicationEvent;

public class PublicationPurchasedEvent extends ApplicationEvent {
  public PublicationPurchasedEvent(Publication publication) {
    super(publication);
  }

  public Publication getSource() {
    return (Publication)super.getSource();
  }
}
