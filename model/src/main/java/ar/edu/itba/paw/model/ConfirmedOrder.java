package ar.edu.itba.paw.model;

public class ConfirmedOrder {

	private final long publication_id;
	private final String buyer;
	private final int quantity;
	private final boolean paid;
	private final boolean received;

	public ConfirmedOrder(final long publication_id, final String buyer, final int quantity, final boolean paid, final boolean received) {
		this.publication_id = publication_id;
		this.buyer = buyer;
		this.quantity = quantity;
		this.paid = paid;
		this.received = received;
	}

	public ConfirmedOrder(final long publication_id, final String buyer, final int quantity) {
		this(publication_id,buyer,quantity,false,false);
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

	public boolean getPaid() {
		return paid;
	}

	public boolean getReceived() {
		return received;
	}
}