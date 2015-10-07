package tools;

import information.Information;
import tools.Tool;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 */
public class ToolTest {

    public ToolTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testGetPuissance(){
    	System.out.println("Test getPuissance Tool");
    	Information<Float> info=new Information<Float>();
    	info.add(1f);
    	info.add(2f);
    	assertEquals(Tool.getPuissance(info), 2.5,0.1f);
    }
    
    @Test
    public void testdBToLin(){
    	System.out.println("Test dBToLin Tool");
    	float val=1;
    	assertEquals(Tool.dBToLin(val), 1.25893,0.0001f);
    }
    

}
