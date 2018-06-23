package ar.edu.itba.paw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@Column(length = 40, nullable = false)
	private String email;
	
	@Column(name = "reg_date")
	@Temporal(TemporalType.DATE)
	private String regdate;
	
	@Column
	private int transactions;
	
	public User(final long id, final String username, final String email, final String password, final String regdate, final int transactions) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.regdate = regdate;
		this.transactions = transactions;
	}
	
	public User(final long id, final String username, final String email, final String password) {
		this(id,username,email,password,"",0);
	}
	
	public User(final long id, final String username, final String email, final String password, final int transactions) {
		this(id,username,email,password,"",transactions);
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
	
	public String getRegdate() {
		return this.regdate;
	}
	
	public int getTransactions() {
		return this.transactions;
	}	
}
