package seng202.team1.util;

public class NotEnoughStockException extends RuntimeException {

    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String errorMessage) {
        super(errorMessage);
    }
}
