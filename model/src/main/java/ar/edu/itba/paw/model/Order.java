package ar.edu.itba.paw.model;

public class Order {

	private final long publication_id;
	private final String subscriber;
	private final int quantity;
	private final boolean confirmed;
	private Publication publication;
	private User subscriberUser;

	public Order(final long publication_id, final String subscriber, final int quantity, final boolean confirmed) {
		this.publication_id = publication_id;
		this.subscriber = subscriber;
		this.quantity = quantity;
		this.confirmed = confirmed;
	}

	public Order(final long publication_id, final String subscriber, final int quantity) {
		this(publication_id,subscriber,quantity,false);
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

	public boolean getConfirmed() {
		return confirmed;
	}
	
	public void setPublication(Publication publication) {
		this.publication = publication;
	}
	
	public Publication getPublication() {
		return publication;
	}
	
	public void setSubscriberUser(User user) {
		this.subscriberUser = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (publication_id ^ (publication_id >>> 32));
		result = prime * result + ((subscriber == null) ? 0 : subscriber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (publication_id != other.publication_id)
			return false;
		if (subscriber == null) {
			if (other.subscriber != null)
				return false;
		} else if (!subscriber.equals(other.subscriber))
			return false;
		return true;
	}
	
	
	
}