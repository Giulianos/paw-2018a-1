package ar.edu.itba.paw.model;

public class User {

	private final long id;
	private final String username;
	private final String email;
	private final String password;
	
	public User(final long id , final String username, final String email, final String password) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
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
}
