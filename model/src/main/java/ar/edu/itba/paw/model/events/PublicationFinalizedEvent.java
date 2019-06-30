package ar.edu.itba.paw.model.events;

import ar.edu.itba.paw.model.Publication;
import org.springframework.context.ApplicationEvent;

public class PublicationFinalizedEvent extends ApplicationEvent {
  public PublicationFinalizedEvent(Publication publication) {
    super(publication);
  }

  @Override
  public Publication getSource() {
    return (Publication)super.getSource();
  }
}
