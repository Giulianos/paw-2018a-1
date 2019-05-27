package ar.edu.itba.paw.interfaces.exception;

public class UnauthorizedAccessException extends Exception {
    private String reason;

    public UnauthorizedAccessException(final String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "UnauthorizedAccessException{" +
                "reason='" + reason + '\'' +
                '}';
    }
}
