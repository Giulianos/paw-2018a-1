package ar.edu.itba.paw.webapp.dto;

public class ErrorDTO {
    private String message;
    private String field;

    public ErrorDTO(final String message, final String field) {
        this.field = field;
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

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
