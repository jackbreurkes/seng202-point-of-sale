package seng202.team1.util;

/**
 * custom exception for when a class's unique identifying attribute is not valid
 */
public class InvalidDataCodeException extends RuntimeException {

    public InvalidDataCodeException() {
        super();
    }

    public InvalidDataCodeException(String errorMessage) {
        super(errorMessage);
    }

}
