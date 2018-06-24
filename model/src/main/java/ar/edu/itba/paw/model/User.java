package ar.edu.itba.paw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_userid_seq")
	@SequenceGenerator(sequenceName = "users_userid_seq", name = "users_userid_seq", allocationSize = 1)
	@Column(name = "user_id")
	private Long id;
	
	@Column(length = 15, nullable = false, unique = true)
	private String username;
	
	@Column(length = 100, nullable = false)
	private String password;
	
	@Column(length = 40, nullable = false, unique = true)
	private String email;
	
	@Column(name = "reg_date")
	@Temporal(TemporalType.DATE)
	private String regdate;
	
	@OneToMany(mappedBy = "subscriber")
	private List<Order> orders = new ArrayList<>();
	
	public User(final String username, final String email, final String password, final String regdate) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.regdate = regdate;
	}
	
	public User(final String username, final String email, final String password) {
		this(username,email,password,"");
	}

	public User() {
		//hibernate needs this
	}

	public long getId() {
		return this.id;
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
	
	public String getRegdate() {
		return this.regdate;
	}
}
