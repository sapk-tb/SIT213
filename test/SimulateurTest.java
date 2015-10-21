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
     * Test of main method, of class Simulateur.
     */
    /*
    @Test
    public void testMain() {
        System.out.println("Test main");
        String[] args = ("-mess 1000").split(" ");
        Simulateur.main(args);
    }
    */
    
    
    
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
     * Test of analyze of args (nbEch)of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsnbEchInvalide() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentsEmpty");
        String[] args = ("-nbEch").split(" ");
        new Simulateur(args);
    }
    
    /**
     * Test of analyze of args (nbEch), of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsnbEchLettreInvalide() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentsEmpty");
        String[] args = ("-nbEch A").split(" ");
        new Simulateur(args);
    }
    
    /**
     * Test of analyze of args (nbEch) of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsnbEchInvalideNul() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentsEmpty");
        String[] args = ("-nbEch 0").split(" ");
        new Simulateur(args);
    }
    
    /**
     * Test of analyze of args (nbEch) of class Simulateur.
     */
    @Test
    public void testAnalyseArgumentsnbEch() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentsnbEchValide");
        String[] args = ("-nbEch 100").split(" ");
        new Simulateur(args);
    }
    
    /**
     * Test of analyze of args (ampl) of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsAmplInvalide() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentsAmplInvalide");
        String[] args = ("-ampl 3 1").split(" ");
        new Simulateur(args);
    }
    
    /**
     * Test of analyze of args (ampl) of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsAmplMaxInvalide() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentsAmplInvalide");
        String[] args = ("-ampl  ").split(" ");
        new Simulateur(args);
    }
    
    /**
     * Test of analyze of args (ampl) of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsAmplMinInvalide() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentsAmplInvalide");
        String[] args = ("-ampl 3").split(" ");
        new Simulateur(args);
    }
    
    /**
     * Test of analyze of args (form) of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsFormInvalide() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentsFormInvalide");
        String[] args = ("-form NRTE").split(" ");
        new Simulateur(args);
    }    
    
    /**
     * Test of analyze of args (form) of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsForm2Invalide() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentsForm2Invalide");
        String[] args = ("-form ").split(" ");
        new Simulateur(args);
    } 

    /**
     * Test of analyze of args (seed) of class Simulateur.
     * @throws ArgumentsException
     */
    @Test
    public void testAnalyseArgumentsSeedValide() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentSeed");
        String[] args = ("-seed 2").split(" ");
        new Simulateur(args);
    } 
    
    /**
     * Test of analyze of args (seed) of class Simulateur.
     * @throws ArgumentsException
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsSeedInvalide() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentSeedInvalide");
        String[] args = ("-seed A").split(" ");
        new Simulateur(args);
    } 
    
    /**
     * Test of analyze of args (s) of class Simulateur.
     */
    @Test
    public void testAnalyseArgumentsVisualisation() throws ArgumentsException, Exception {
        System.out.println("Test AnalyseArgumentSimulation");
        String[] args = ("-s").split(" ");
        new Simulateur(args);
    } 
    
    /**
     * Test of analyze of args (mess) of class Simulateur.
     * @throws Exception 
     * @throws ArgumentsException 
     */
    @Test
    public void testAnalyseArgumuentsMessValideFixe() throws ArgumentsException, Exception {
        System.out.println("Test mess");
        String[] args = ("-mess 10010110101010110").split(" ");
        new Simulateur(args);
    }
    
    /**
      * Test of analyze of args (mess) of class Simulateur.
     * @throws Exception 
     * @throws ArgumentsException 
     */
    @Test
    public void testAnalyseArgumuentsMessValideAleatoire() throws ArgumentsException, Exception {
        System.out.println("Test mess");
        String[] args = ("-mess 2445").split(" ");
        new Simulateur(args);
    }
    
    /**
      * Test of analyze of args (snr) of class Simulateur.
     * @throws Exception 
     * @throws ArgumentsException 
      */
    @Test
    public void testAnalyseArgumuentsSNRValide() throws ArgumentsException, Exception {
        System.out.println("Test snrn");
        String[] args = ("-snr 5").split(" ");
        new Simulateur(args);
    }
    
    /**
     * Test of analyze -ti, of class Simulateur.
     * @throws Exception 
     * @throws ArgumentsException 
     */
    @Test
    public void testAnalyseArgumentsTIValide() throws ArgumentsException, Exception {
        System.out.println("Test -ti valide");
        String[] args = ("-ti 1 1 0.3").split(" ");
        new Simulateur(args);
    }
    
    /**
     * Test of analyze -ti, of class Simulateur.
     */
   
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsTIInvalide() throws ArgumentsException, Exception{
        System.out.println("Test -ti invalide");
        String[] args = ("-ti").split(" ");
        new Simulateur(args);
    }
    
    
    /**
     * Test of analyze -ti, of class Simulateur.
     */
    // vérifier si param ar et dt sont bien passés
    // pour chaque trajectoire
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsTIInvalide2() throws ArgumentsException, Exception {
        System.out.println("Test -ti invalide");
        String[] args = ("-ti 1 2").split(" "); 
        new Simulateur(args);
    }
    
    /**
     * Test of analyze -ti, of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsTIInvalide3() throws ArgumentsException, Exception{
        System.out.println("Test -ti invalide");
        String[] args = ("-ti 0 2 3").split(" "); //0 trajet mais des données passees
        new Simulateur(args);
    }
    
    /**
     * Test of analyze -ti, of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsTIInvalide4() throws ArgumentsException, Exception{
        System.out.println("Test -ti invalide");
        String[] args = ("-ti 1 5 0.5 2").split(" "); //manque données
        new Simulateur(args);
    }
    
    /**
     * Test of analyze -ti, of class Simulateur.
     * @throws Exception 
     * @throws ArgumentsException 
     */
    @Test
    public void testAnalyseArgumentsTIValide5() throws ArgumentsException, Exception{
        System.out.println("Test -ti valide");
        String[] args = ("-ti 1 5 0.5 -ti 2 7 0.7 -ti 1 5 0.3").split(" "); //0 trajet mais des données passees
        new Simulateur(args);
    }
    
    /**
     * Test of analyze -ti, of class Simulateur.
     * @throws Exception 
     * @throws ArgumentsException 
     */
    @Test
    public void testAnalyseArgumentsTIValide6() throws ArgumentsException, Exception{
        System.out.println("Test -ti valide");
        String[] args = ("-ti 1 5 0.5 -ti 2 7 0.7").split(" "); //0 trajet mais des données passees
        new Simulateur(args);
    }
    
    /**
     * Test of analyze -ti, of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsTIInvalide7() throws ArgumentsException, Exception{
        System.out.println("Test -ti invalide");
        String[] args = ("-ti 1 0.8 -ti").split(" "); //manque données
        new Simulateur(args);
    }

    
    /**
     * Test of analyze -ti, of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsTIInvalide8() throws ArgumentsException, Exception{
        System.out.println("Test -ti invalide");
        String[] args = ("-ti 1 0.8 -ti 2").split(" "); //manque données
        new Simulateur(args);
    }
    
    /**
     * Test of analyze -ti, of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsTIInvalide9() throws ArgumentsException, Exception{
        System.out.println("Test -ti invalide");
        String[] args = ("-ti 1 -ti 2").split(" "); //manque données
        new Simulateur(args);
    }
    
    /**
     * Test of analyze -ti, of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsTIInvalide10() throws ArgumentsException, Exception{
        System.out.println("Test -ti invalide");
        String[] args = ("-ti 6 3 0.4").split(" "); //manque données
        new Simulateur(args);
    }
    
    /**
     * Test of analyze -ti, of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsTIInvalide11() throws ArgumentsException, Exception{
        System.out.println("Test -ti invalide");
        String[] args = ("-ti 2 -ti 2 0.3").split(" "); //manque données
        new Simulateur(args);
    }
    
    /**
     * Test of analyze -ti, of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsTIInvalide12() throws ArgumentsException, Exception{
        System.out.println("Test -ti invalide");
        String[] args = ("-ti -ti -ti").split(" "); //manque données
        new Simulateur(args);
    }

    /**
     * Test of analyze -ti, of class Simulateur.
     */
    @Test(expected = ArgumentsException.class)
    public void testAnalyseArgumentsTransducteur() throws ArgumentsException, Exception{
        System.out.println("Test -transducteur");
        String[] args = ("-transducteur").split(" "); //manque données
        new Simulateur(args);
    }

    /**
     * Test of analyseArguments method, of class Simulateur.
     */
    @Test
    public void testAnalyseArguments() throws Exception {
        System.out.println("analyseArguments");
        String[] args = null;
        Simulateur instance = null;
        instance.analyseArguments(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of execute method, of class Simulateur.
     */
    @Test
    public void testExecute() throws Exception {
        System.out.println("execute");
        Simulateur instance = null;
        instance.execute();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculTauxErreurBinaire method, of class Simulateur.
     */
    @Test
    public void testCalculTauxErreurBinaire() {
        System.out.println("calculTauxErreurBinaire");
        Simulateur instance = null;
        double expResult = 0.0F;
        double result = instance.calculTauxErreurBinaire();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Simulateur.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Simulateur.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
    
}
