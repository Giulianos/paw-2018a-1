package ar.edu.itba.paw.interfaces.exception;

public class PublicationFulfilledException extends Exception {
    private String reason;

    public PublicationFulfilledException(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "PublicationFulfilledException{" +
                "reason='" + reason + '\'' +
                '}';
    }
}
