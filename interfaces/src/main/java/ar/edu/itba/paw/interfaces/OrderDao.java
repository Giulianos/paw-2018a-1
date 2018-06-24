package ar.edu.itba.paw.interfaces;

import java.util.List;

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
	 * @return The orders with the provided publication id.
	 */
	
	public List<Order> findByPublication(Publication publication);
	
	/**
	 * Creates a new order.
	 * 
	 * @param publication The publication.
	 * @param subscriber The subscriber.
	 * @param quantity The quantity of the order.
	 * 
	 * @return The created order.
	 */
	
	public Order create(Publication publication, User subscriber, int quantity);
	
	/**
	 * Sets the is_confirmed attribute
	 * @param order The order to be confirmed
	 * 
	 * @return True if the set was successful.
	 */
	
	public boolean confirm(Order order);
	
	/**
	 * Delete all orders for the corresponding publication id.
	 * 
	 * @param publication The publication.
	 * 
	 * @return True if the deletion was successful.
	 */
	
	public boolean delete(Publication publication);

	/**
	 * Delete the order 
	 * 
	 * @param order The order to be deleted
	 * 
	 * @return True if the deletion was successful.
	 */
	
	public boolean delete(Order order);
}