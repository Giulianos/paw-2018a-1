package ar.edu.itba.paw.model;

public class User {

	private final Integer id;
	private final String username;
	
	public User(Integer id, String username) {
		this.id = id;
		this.username = username;
	}

	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}
	
}
