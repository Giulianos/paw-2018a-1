package ar.edu.itba.paw.interfaces.dao;

import java.util.List;
import java.util.Optional;

import ar.edu.itba.paw.model.Message;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.User;

public interface MessageDao {

	/**
	 * Creates a new message
	 * @param from Sender of the message
	 * @param to Receiver of the message 
	 * @param order Order of which the message is about
	 * @param text Text of the message (body)
	 * @return The created message
	 */
	public Optional<Message> create(final User from, final User to, final Order order, final String text);
	
	/**
	 * Retreives the messages that corresponds to a certain order
	 * @param order The order to get the messages from
	 * @return The retreived messages
	 */
	public List<Message> retreiveFromOrder(final Order order);
	
}
