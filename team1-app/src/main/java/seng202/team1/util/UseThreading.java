package seng202.team1.util;

/**
 * simple utility class containing a single boolean attribute setting whether threading should be used.
 */
public class UseThreading {

    public static boolean using = false;

    public static void setUsing(boolean using) {
        UseThreading.using = using;
    }
}
