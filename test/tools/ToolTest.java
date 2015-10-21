package tools;

import information.Information;

import java.util.Arrays;

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
    	Information<Double> info=new Information<Double>();
    	info.add(1.0);
    	info.add(2.0);
    	assertEquals(Tool.getPuissance(info), 2.5,0.1f);
    }
    
    //Test de renvoit nullpointer execption
    @Test(expected = NullPointerException.class)
    public void testGetPuissanceNullPointer(){
    	System.out.println("Test getPuissance Tool - NullPointerException");
    	Information<Double> info = null;
    	assertEquals(Tool.getPuissance(info), 2.5,0.1f);
    }
    
    @Test
    public void testdBToLin(){
    	System.out.println("Test dBToLin Tool");
    	double val=1;
    	assertEquals(Tool.dBToLin(val), 1.25893,0.0001f);
    }
    
    //tous les bits sont erronés : 1f
	@Test
    public void testCompare(){
    	System.out.println("Test Compare Tool - tous les bits sont erronés");
    
    	//définition de deux tableaux de test
    	Boolean [] a = new Boolean[100];
    	Boolean [] b = new Boolean[100];
    	
    	//premier que des 1 (true)
    	Arrays.fill(a,true);
    	//deuxième que des 0 (false)
    	Arrays.fill(b,false);

    	assertEquals(Tool.compare(a, b), 1f, 0); //Sans delta
    }
  
	
	//la moitié des bits sont erronés : 0.5f
	@Test
    public void testCompare2(){
    	System.out.println("Test Compare Tool - moitié bits erronés");
    
    	//définition de deux tableaux de test
    	Boolean [] a = new Boolean[100];
    	Boolean [] b = new Boolean[100];
    	
    	//premier que des 1 (true)
    	Arrays.fill(a,true);
    	//deuxième que des 0 (false)
    	Arrays.fill(b,false);
    	
    	for(int i = 0; i <50;i++)
    	{
    		b[i] = true;
    	}

    	assertEquals(Tool.compare(a, b), 0.5f, 0); //Sans delta
    }

}
