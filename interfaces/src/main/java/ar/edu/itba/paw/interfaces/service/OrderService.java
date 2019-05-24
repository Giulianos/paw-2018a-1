package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

public interface OrderService {

  /**
   * Create an order for logged user in publication
   * @param publication
   * @param quantity
   * @return created order
   */
  public Order create(final Publication publication, final Long quantity);

}
