package ar.edu.itba.paw.model.events;

import ar.edu.itba.paw.model.Order;
import org.springframework.context.ApplicationEvent;

public class NewMessageEvent extends ApplicationEvent {
  public NewMessageEvent(Order order) {
    super(order);
  }

  public Order getSource() {
    return (Order)super.getSource();
  }
}
