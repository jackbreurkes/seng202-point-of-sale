package seng202.team1.util;

public class InvalidOrderStatusException extends RuntimeException {

    public InvalidOrderStatusException() {
        super();
    }

    public InvalidOrderStatusException(String errorMessage) {
        super(errorMessage);
    }

}
