package seng202.team1.util;

/**
 * custom exception to use when trying to transition an order into an invalid next state.
 */
public class InvalidOrderStatusException extends RuntimeException {

    public InvalidOrderStatusException() {
        super();
    }

    public InvalidOrderStatusException(String errorMessage) {
        super(errorMessage);
    }

}
