package ar.edu.itba.paw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_ordid_seq")
	@SequenceGenerator(sequenceName = "order_ordid_seq", name = "publications_ordid_seq", allocationSize = 1)
	@Column(name = "order_id")
	private Long order_id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Publication publication;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private User subscriber;
	
	@Column
	private Integer quantity;
	
	@Column
	private Boolean confirmed;
	
	public Order(final Publication publication, final User subscriber, final int quantity, final boolean confirmed) {
		this.publication = publication;
		this.subscriber = subscriber;
		this.quantity = quantity;
		this.confirmed = confirmed;
	}

	public Order(final Publication publication, final User subscriber, final int quantity) {
		this(publication,subscriber,quantity,false);
	}

	public User getSubscriber() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (publication.hashCode() ^ (publication.hashCode() >>> 32));
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
	
	
	
	public User getSubscriberUser() {
		return subscriberUser;
	}
		
}