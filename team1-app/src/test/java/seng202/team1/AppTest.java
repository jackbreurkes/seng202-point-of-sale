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
    @Test
    public void testApp()
    {
        assertTrue( true );
    }
    
    @Test
    public void jacksTest()
    {
        assertTrue( 1 + 1 == 2 );
    }

    @Test
    public void davidsTest()
    {
        assertTrue( 10 + 10 == 20 );
    }

    @Test
    public void euansTest() {
        assertTrue( 1 * 2020 == 2020 );
    }

}
