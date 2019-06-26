package ar.edu.itba.paw.model.events;

import ar.edu.itba.paw.model.Publication;
import org.springframework.context.ApplicationEvent;

public class PublicationFulfilledEvent extends ApplicationEvent {
    public PublicationFulfilledEvent(Publication publication) {
        super(publication);
    }
}
