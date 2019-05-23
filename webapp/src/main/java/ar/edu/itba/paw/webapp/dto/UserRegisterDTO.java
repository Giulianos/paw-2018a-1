package ar.edu.itba.paw.webapp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterDTO {

    @NotNull
    @Size(min=3, max = 15)
    private String name;

    @NotNull
    private String email;

    @NotNull
    @Size(min = 8)
    private String password;

    public UserRegisterDTO() {
        // Empty constructor needed by JAX-RS
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
