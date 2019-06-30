package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.interfaces.exception.EntityNotFoundException;
import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.model.compositepks.OrderId;

public interface ReviewService {
  /**
   * Review how supervisor handled order
   * @param orderId
   * @param comment
   * @param rating
   */
  public void reviewOrder(final OrderId orderId, final String comment, final Integer rating) throws EntityNotFoundException, UnauthorizedAccessException;
}
