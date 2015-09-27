package transmetteurs;

import information.Information;
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
public class TransmetteurParfaitTest {

    public TransmetteurParfaitTest() {
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
     * Test of recevoir method, of class TransmetteurParfait.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testRecevoir() throws Exception {
        System.out.println("Test recevoir");
        Boolean bites[] = {true, false, true};
        Information<Boolean> information = new Information<>(bites);
        TransmetteurParfait instance = new TransmetteurParfait();
        instance.recevoir(information);

        assertEquals(instance.informationRecue, information);
        //Comme recevoir lance emettre cette donction est aussi testé ici. 
        assertEquals(instance.informationEmise, information);
    }

    /**
     * Test of emettre method, of class TransmetteurParfait.
     */
    @Test
    public void testEmettre() throws Exception {
        System.out.println("emettre");
        TransmetteurParfait instance = new TransmetteurParfait();
        instance.emettre();
        assertEquals(instance.informationEmise, null);
    }

}
