package ar.edu.itba.paw.interfaces;

import java.util.List;
import java.util.Optional;

import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

public interface OrderDao {
	
	/**
	 * Finds orders with the provided subscriber.
	 * 
	 * @param subscriber The subscriber.
	 * 
	 * @return The orders with the provided subscriber.
	 */
	
	public List<Order> findBySubscriber(User subscriber);

	
	/**
	 * Finds the orders with the provided publication.
	 * 
	 * @param publication The publication.
	 * 
	 * @return The orders with the provided publication.
	 */
	
	public List<Order> findByPublication(Publication publication);
	
	/**
	 * Finds the order with the provided publication and subscriber.
	 * 
	 * @param publication The publication.
	 * 
	 * @param supervisor The subscriber.
	 * 
	 * @return The order with the provided publication and subscriber.
	 */
	
	public Optional<Order> findByPublicationAndSupervisor(Publication publication, User subscriber);
	
	/**
	 * Creates a new order.
	 * 
	 * @param publication The publication.
	 * @param subscriber The subscriber.
	 * @param quantity The quantity of the order.
	 * 
	 * @return The created order.
	 */
	
	public Optional<Order> create(Publication publication, User subscriber, int quantity);
	
	/**
	 * Sets the is_confirmed attribute
	 * @param order The order to be confirmed
	 * 
	 * @return True if the set was successful.
	 */
	
	public boolean confirm(Order order);
	
	/**
	 * Returns if order is confirmed
	 * @param order The order
	 * 
	 * @return True if the order is confirmed.
	 */
	
	public boolean isConfirm(Order order);
	

	/**
	 * Delete the order 
	 * 
	 * @param order The order to be deleted
	 * 
	 * @return True if the deletion was successful.
	 */
	
	public boolean delete(Order order);
	
	/**
	 * Updates the order object.
	 * 
	 * @param order The order to be updated.
	 * 
	 * @return true if the update was successful.
	 */
	public boolean updateOrder(Order order);
}