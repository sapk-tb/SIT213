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
    
    
    /**
     * Test of analyze of args of class Simulateur.
     */
    @Test
    public void testAnalyseArgumentsEmpty() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentsEmpty");
        String[] args = {};
        new Simulateur(args);
    }
    
    /**
     * Test of analyze of args of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsnbEchInvalide() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentsEmpty");
        String[] args = ("-nbEch").split(" ");
        new Simulateur(args);
    }
    
    /**
     * Test of analyze of args of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsAmplInvalide() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentsAmplInvalide");
        String[] args = ("-ampl 3 1").split(" ");
        new Simulateur(args);
    }
    
    /**
     * Test of analyze of args of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsFormInvalide() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentsFormInvalide");
        String[] args = ("-form NRTE").split(" ");
        new Simulateur(args);
    }    
    
    /**
     * Test of analyze of args of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsForm2Invalide() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentsForm2Invalide");
        String[] args = ("-form ").split(" ");
        new Simulateur(args);
    }    
    
}
