package ar.edu.itba.paw.webapp.dto;

public class UserRegisterDTO {
    private String username;
    private String email;
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
