package seng202.team1.util;

public class InvalidDataCodeException extends RuntimeException {

    public InvalidDataCodeException() {
        super();
    }

    public InvalidDataCodeException(String errorMessage) {
        super(errorMessage);
    }

}
