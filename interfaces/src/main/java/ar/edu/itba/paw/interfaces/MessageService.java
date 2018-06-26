package ar.edu.itba.paw.interfaces;

import java.util.List;
import java.util.Optional;

import ar.edu.itba.paw.model.Message;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.User;

public interface MessageService {
	/**
	 * Sends an email to the other User on order
	 * @param me The user sending the message
	 * @param order The order to which the message will be send
	 * @param text The text of the message
	 * @return The sended message
	 */
	public Optional<Message> sendMessage(User me, Order order, String text);
	
	/**
	 * Retreives a list of messages for an order
	 * @param order The order to get the messages from
	 * @return The list of messages
	 */
	public List<Message> retreiveFromOrder(Order order);
}
