package seng202.team1;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    public void jacksTest()
    {
        assertTrue( 1 + 1 == 2 );
    }

    public void davidsTest()
    {
        assertTrue( 10 + 10 == 20 );
    }

    public void euansTest() {
        assertTrue( 1 * 2020 == 2020 );
    }

}
