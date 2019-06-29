package ar.edu.itba.paw.model.events;

import ar.edu.itba.paw.model.Order;
import org.springframework.context.ApplicationEvent;

public class OrderConfirmedEvent extends ApplicationEvent {
  public OrderConfirmedEvent(Order order) {
    super(order);
  }

  @Override
  public Order getSource() {
    return (Order)super.getSource();
  }
}
