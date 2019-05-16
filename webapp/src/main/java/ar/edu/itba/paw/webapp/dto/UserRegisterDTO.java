package ar.edu.itba.paw.webapp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterDTO {

    @NotNull
    @Size(max = 15)
    private String username;


    private String email;

    @NotNull
    @Size(min = 8, message = "Password must contain at least 8 characters")
    private String password;

    public UserRegisterDTO() {
        // Empty constructor needed by JAX-RS
    }

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
}
