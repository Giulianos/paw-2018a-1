package ar.edu.itba.paw.model.events;

import org.springframework.context.ApplicationEvent;

public class SupervisorLeftEvent extends ApplicationEvent {
    public SupervisorLeftEvent(Object source) {
        super(source);
    }
}
