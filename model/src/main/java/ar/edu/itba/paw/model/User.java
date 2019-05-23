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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "users")
public class User extends TimestampedEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long user_id;
	
	@Column(length = 15, nullable = false)
	private String name;
	
	@Column(length = 100, nullable = false)
	private String password;
	
	@Column(length = 40, nullable = false, unique = true)
	private String email;

	public User(final String name, final String email, final String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public User() {
		//hibernate needs this
	}

	public Long getId() {
		return this.user_id;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPassword() {
		return this.password;
	}

	public String getName() {
		return this.name;
	}

}
