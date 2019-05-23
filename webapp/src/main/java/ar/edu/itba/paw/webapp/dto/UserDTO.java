package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.User;

public class UserDTO {

    private String name;
    private String email;
    private Long id;

    public UserDTO(User u) {
        this.name = u.getName();
        this.email = u.getEmail();
        this.id = u.getId();
    }

    public UserDTO() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
