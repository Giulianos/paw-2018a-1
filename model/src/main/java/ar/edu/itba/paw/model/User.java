package ar.edu.itba.paw.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "users")
public class User {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long user_id;
	
	@Column(length = 15, nullable = false, unique = true)
	private String username;
	
	@Column(length = 100, nullable = false)
	private String password;
	
	@Column(length = 40, nullable = false, unique = true)
	private String email;
	
	@Column(name = "reg_date")
	@Temporal(TemporalType.DATE)
	private Date regdate;
	
	@OneToMany(mappedBy = "subscriber", fetch = FetchType.EAGER)
	private List<Order> orders = new ArrayList<>();
	
	public User(final String username, final String email, final String password, final Date regdate) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.regdate = regdate;
	}
	
	public User(final String username, final String email, final String password) {
		this(username,email,password,Date.from(Instant.now()));
	}

	public User() {
		//hibernate needs this
	}

	public Long getId() {
		return this.user_id;
	}

	public String getUsername() {
		return this.username;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public List<Order> getOrders() {
		return this.orders;
	}
	
	public Date getRegdate() {
		return this.regdate;
	}
}
