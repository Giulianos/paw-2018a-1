package ar.edu.itba.paw.model;

public class ConfirmedOrder {

	private final long publication_id;
	private final String buyer;
	private final int quantity;

	public ConfirmedOrder(final long publication_id, final String buyer, final int quantity) {
		this.publication_id = publication_id;
		this.buyer = buyer;
		this.quantity = quantity;
	}

	public long getPublication_id() {
		return publication_id;
	}

	public String getBuyer() {
		return buyer;
	}

	public int getQuantity() {
		return quantity;
	}
}