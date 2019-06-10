package ar.edu.itba.paw.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends TimestampedEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@Access(AccessType.PROPERTY)
	private Long id;
	
	@Column(length = 30, nullable = false)
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
		return this.id;
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

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id.equals(user.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
