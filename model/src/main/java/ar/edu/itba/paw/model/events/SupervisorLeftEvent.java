package ar.edu.itba.paw.model.events;

import ar.edu.itba.paw.model.Publication;
import org.springframework.context.ApplicationEvent;

public class SupervisorLeftEvent extends ApplicationEvent {
    public SupervisorLeftEvent(Publication publication) {
        super(publication);
    }
}
