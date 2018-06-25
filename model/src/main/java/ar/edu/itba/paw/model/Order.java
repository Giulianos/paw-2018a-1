package ar.edu.itba.paw.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

	@Embeddable
	public static class OrderId implements Serializable {
		private static final long serialVersionUID = 1L;

		@Column(name = "fk_subscriber")
		protected Long subscriberId;
		
		@Column(name = "fk_publication")
		protected Long publicationId;
		
		public OrderId(Long subscriberId, Long publicationId) {
			this.subscriberId = subscriberId;	
			this.publicationId = publicationId;
		}
		
		public OrderId() {
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((subscriberId == null) ? 0 : subscriberId.hashCode());
			result = prime * result
					+ ((publicationId == null) ? 0 : publicationId.hashCode());
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
			
			OrderId other = (OrderId) obj;
			
			if (subscriberId == null) {
				if (other.subscriberId != null)
					return false;
			} else if (!subscriberId.equals(other.subscriberId))
				return false;
			
			if (publicationId == null) {
				if (other.publicationId != null)
					return false;
			} else if (!publicationId.equals(other.publicationId))
				return false;
			
			return true;
		}
	}
	
	@EmbeddedId
	private OrderId id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "fk_publication", insertable = false, updatable = false)
	private Publication publication;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "fk_subscriber", insertable = false, updatable = false)
	private User subscriber;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
	private List<Message> messages;
	
	@Column
	private Integer quantity;
	
	@Column
	private Boolean confirmed;
	
	public Order(final Publication publication, final User subscriber, final int quantity, final boolean confirmed) {
		this.id = new OrderId(subscriber.getId(), publication.getId());
		this.publication = publication;
		this.subscriber = subscriber;
		this.quantity = quantity;
		this.confirmed = confirmed;
		
		publication.getOrders().add(this);
		subscriber.getOrders().add(this);
	}

	public Order(final Publication publication, final User subscriber, final int quantity) {
		this(publication,subscriber,quantity,false);
	}
	
	public Order() {
		//hibernate needs this
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
	
	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}