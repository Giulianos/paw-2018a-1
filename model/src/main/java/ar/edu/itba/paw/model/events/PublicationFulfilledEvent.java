package ar.edu.itba.paw.model.events;

import org.springframework.context.ApplicationEvent;

public class PublicationFulfilledEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public PublicationFulfilledEvent(Object source) {
        super(source);
    }
}
