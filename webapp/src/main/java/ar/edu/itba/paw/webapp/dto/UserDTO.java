package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.User;

public class UserDTO {

    private String name;
    private Long id;

    public UserDTO(User u) {
        this.name = u.getName();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
