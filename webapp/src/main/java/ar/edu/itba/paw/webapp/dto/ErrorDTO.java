package ar.edu.itba.paw.webapp.dto;

public class ErrorDTO {
    private String message;

    public ErrorDTO(final String message) {
        this.message = message;
    }

    public ErrorDTO() {
        // Empty constructor needed by JAX-RS
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
