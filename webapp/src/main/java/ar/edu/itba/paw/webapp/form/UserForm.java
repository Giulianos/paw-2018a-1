package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class UserForm {
	@Size(min = 6, max = 15, message="{user.size}")
	@Pattern(regexp = "[a-zA-Z][a-zA-Z0-9]+", message="{register.user.pattern}")
	private String username;

	@Size(min = 6, max = 40, message="{email.size}")
	@Email(message="{register.email.pattern}")
	private String email;

	@Size(min = 6, max = 30, message="{password.size}")
	private String password;

	private String repeatPassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRepeatPassword() {
		return repeatPassword;
	}
	
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	
	public boolean passwordCheck() {
		return getPassword().equals(getRepeatPassword());
	}
}