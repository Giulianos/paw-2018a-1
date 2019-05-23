package ar.edu.itba.paw.webapp.dto;

public class UserLoginDTO {
    private String email;
    private String password;

    public UserLoginDTO() {
        // Empty constructor needed by JAX-RS
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
