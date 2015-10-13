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
    public void testAnalyseArgumentsnbEchLettreInvalide() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentsEmpty");
        String[] args = ("-nbEch A").split(" ");
        new Simulateur(args);
    }
    
    /**
     * Test of analyze of args of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsnbEchInvalideNul() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentsEmpty");
        String[] args = ("-nbEch 0").split(" ");
        new Simulateur(args);
    }
    
    /**
     * Test of analyze of args of class Simulateur.
     */
    @Test
    public void testAnalyseArgumentsnbEch() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentsnbEchValide");
        String[] args = ("-nbEch 100").split(" ");
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
    public void testAnalyseArgumentsAmplMaxInvalide() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentsAmplInvalide");
        String[] args = ("-ampl  ").split(" ");
        new Simulateur(args);
    }
    
    /**
     * Test of analyze of args of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsAmplMinInvalide() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentsAmplInvalide");
        String[] args = ("-ampl 3").split(" ");
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

    /**
     * Test of analyze of args of class Simulateur.
     */
    @Test
    public void testAnalyseArgumentsSeedValide() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentSeed");
        String[] args = ("-seed 2").split(" ");
        new Simulateur(args);
    } 
    
    /**
     * Test of analyze of args of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsSeedInvalide() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentSeedInvalide");
        String[] args = ("-seed A").split(" ");
        new Simulateur(args);
    } 
    
    /**
     * Test of analyze of args of class Simulateur.
     */
    @Test
    public void testAnalyseArgumentsVisualisation() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentSimulation");
        String[] args = ("-s").split(" ");
        new Simulateur(args);
    } 
    
    /**
     * Test of main method, of class Simulateur.
     */
    @Test
    public void testAnalyseArgmuentsMessValideFixe() {
        System.out.println("Test main");
        String[] args = ("-mess 10010110101010110").split(" ");
        Simulateur.main(args);
    }
    
    /**
     * Test of main method, of class Simulateur.
     */
    @Test
    public void testAnalyseArgmuentsMessValideAleatoire() {
        System.out.println("Test main");
        String[] args = ("-mess 2445").split(" ");
        Simulateur.main(args);
    }
    
    /**
     * Test of main method, of class Simulateur.
     */
    @Test
    public void testAnalyseArgmuentsSNRValide() {
        System.out.println("Test main");
        String[] args = ("-snr 5").split(" ");
        Simulateur.main(args);
    }
    
    /**
     * Test of analyze -ti, of class Simulateur.
     */
    @Test
    public void testAnalyseArgmuentsTIValide() {
        System.out.println("Test -ti valide");
        String[] args = ("-ti 1 1 0.0f").split(" ");
        Simulateur.main(args);
    }
    
    /**
     * Test of analyze -ti, of class Simulateur.
     */
    @Test
    public void testAnalyseArgmuentsTIInvalide() {
        System.out.println("Test -ti invalide");
        String[] args = ("-ti 1 1").split(" ");
        Simulateur.main(args);
    }

    
    
}
