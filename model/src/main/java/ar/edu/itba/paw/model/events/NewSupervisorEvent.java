package ar.edu.itba.paw.model.events;

import ar.edu.itba.paw.model.Publication;
import org.springframework.context.ApplicationEvent;

public class NewSupervisorEvent extends ApplicationEvent {

  public NewSupervisorEvent(Publication publication) {
    super(publication);
  }

}
