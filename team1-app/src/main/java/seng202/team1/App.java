package seng202.team1;

import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        MyObject myObject = new MyObject();
        Gson gson = new Gson();
        String jsonString = gson.toJson(myObject);

        System.out.println(jsonString);
    }
}
