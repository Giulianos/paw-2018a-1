package ar.edu.itba.paw.model;

public class Order {

	private final long publication_id;
	private final String subscriber;
	private final int quantity;

	public Order(final long publication_id, final String subscriber, final int quantity) {
		this.publication_id = publication_id;
		this.subscriber = subscriber;
		this.quantity = quantity;
	}

	public long getPublication_id() {
		return publication_id;
	}

	public String getSubscriber() {
		return subscriber;
	}

	public int getQuantity() {
		return quantity;
	}
}