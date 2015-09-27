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
public class SimulateurTest {
    
    public SimulateurTest() {
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

    /**
     * Test of calculTauxErreurBinaire method, of class Simulateur.
     * @throws java.lang.Exception
     */
    @Test
    public void testCalculTauxErreurBinaire() throws Exception {
        System.out.println("Test calculTauxErreurBinaire");
        //Transmission parfaite
        Simulateur instance_parfaite = new Simulateur(("-mess 1000").split(" "));
        instance_parfaite.execute();
        float expResult = 0.0F;
        float result = instance_parfaite.calculTauxErreurBinaire();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of main method, of class Simulateur.
     */
    
    @Test
    public void testMain() {
        System.out.println("Test main");
        String[] args = ("-mess 1000").split(" ");
        Simulateur.main(args);
    }
    
    
}
