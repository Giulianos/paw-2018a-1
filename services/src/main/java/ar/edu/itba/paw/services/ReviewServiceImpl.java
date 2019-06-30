package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.OrderDao;
import ar.edu.itba.paw.interfaces.dao.ReviewDao;
import ar.edu.itba.paw.interfaces.exception.EntityNotFoundException;
import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.interfaces.service.ReviewService;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.compositepks.OrderId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Optional;

@Primary
@Service
public class ReviewServiceImpl implements ReviewService {

  @Autowired
  ReviewDao reviewDao;
  @Autowired
  OrderDao orderDao;

  @Override
  @Transactional
  public void reviewOrder(OrderId orderId, String comment, Integer rating) throws EntityNotFoundException, UnauthorizedAccessException {
    Optional<Order> order = orderDao.findById(orderId);

    if(!order.isPresent()) {
      throw new EntityNotFoundException();
    }

    final String loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

    if(!order.get().getOrderer().getEmail().equals(loggedUserEmail)) {
      throw new UnauthorizedAccessException("Only the orderer can review the supervisor of this order");
    }

    // Check if user is trying to review himself
    if(order.get().getPublication().getSupervisor().getEmail().equals(loggedUserEmail)) {
      throw new IllegalStateException("The supervisor cannot review himself");
    }

    // Check that the rating is valid (this is redundant as it should be checked by dto validations)
    if(1 > rating || rating > 5) {
      throw new IllegalArgumentException("Rating should be in the 1 to 5 range");
    }

    // Check if the order is confirmed and it isn't from more than 15 days
    final Date limitDate = Date.from(Instant.now().plus(15, ChronoUnit.DAYS));
    if(order.get().getUpdatedAt().before(limitDate)) {
      throw new IllegalStateException("The period to review the supervisor has expired");
    }
    if(!order.get().getPurchaseAccepted()) {
      throw new IllegalStateException("Cannot review an unconfirmed order");
    }

    // Everything ok, create review
    order.get().setReview(new Review(comment, rating));
  }
}
