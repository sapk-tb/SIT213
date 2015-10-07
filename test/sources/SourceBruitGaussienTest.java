package sources;

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
public class SourceBruitGaussienTest {

    public SourceBruitGaussienTest() {
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
    public void testConstructeur1() {
    	System.out.println("Test Constucteur2 SourceBruitGaussien");
        int nbEch=100;
        SourceBruitGaussien instance =new SourceBruitGaussien(nbEch);
        assertEquals(instance.informationGeneree.nbElements(), nbEch);
    }
    
    @Test
    public void testConstructeur2() {
        System.out.println("Test Constucteur2 SourceBruitGaussien");
        int nbEch=1000000;
        float puissance=1000;
        SourceBruitGaussien instance =new SourceBruitGaussien(nbEch, puissance);
        assertEquals(instance.informationGeneree.nbElements(), nbEch);
        assertEquals(Tool.getPuissance(instance.informationGeneree), puissance,0.1*puissance);

    }

}
