package seng202.team1.util;

public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException() {
        super();
    }

    public RecipeNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
