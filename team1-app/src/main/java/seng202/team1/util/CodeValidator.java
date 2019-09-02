package seng202.team1.util;

public class CodeValidator {

    public static final int MIN_CHARS = 3;
    public static final int MAX_CHARS = 10;

    /**
     * checks if a given String is a valid code that can be used as a storage key.
     * if it is valid, the String code is returned.
     * otherwise, an exception is thrown.
     * TODO is the above the best way to handle this?
     * @param code the String to check the validity of
     * @return the
     */
    public static String checkCode(String code) {
        if (code.length() < MIN_CHARS || code.length() > MAX_CHARS) {
            throw new IllegalArgumentException("food item codes must be between 3 and 10 characters (inclusive)");
        }
        if (!code.matches("[A-Z0-9]+")) {
            throw new IllegalArgumentException("food item codes must be uppercase alphanumeric");
        }
        return code;
    }

}
