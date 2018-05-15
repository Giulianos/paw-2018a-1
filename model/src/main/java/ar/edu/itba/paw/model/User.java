package ar.edu.itba.paw.model;

public class User {

	private final long id;
	private final String username;
	private final String email;
	private final String regdate;
	private final int transactions;
	private final String password;
	
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
