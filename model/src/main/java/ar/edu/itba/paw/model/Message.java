package ar.edu.itba.paw.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
	@SequenceGenerator(sequenceName = "message_seq", name = "message_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Order order;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User from;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User to;
	
	@Column
	private String text;
	
	@Column
	private Boolean read;

	public Message(Order order, User from, User to, String text, Boolean read) {
		super();
		this.order = order;
		this.from = from;
		this.to = to;
		this.text = text;
		this.read = read;
	}
	
	public Message() {
		//hibernate needs this
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		Message other = (Message) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
