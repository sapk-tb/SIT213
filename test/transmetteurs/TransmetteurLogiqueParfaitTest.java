package transmetteurs;

import destinations.DestinationFinale;
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
public class TransmetteurLogiqueParfaitTest {

    public TransmetteurLogiqueParfaitTest() {
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
     * Test of recevoir method, of class TransmetteurLogiqueParfait.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testRecevoir() throws Exception {
        System.out.println("Test recevoir");
        Boolean bits[] = {true, false, true};
        Information<Boolean> information = new Information<>(bits);
        TransmetteurLogiqueParfait instance = new TransmetteurLogiqueParfait();
        DestinationFinale dest = new DestinationFinale();
        instance.connecter(dest);
        instance.recevoir(information);

        assertEquals(instance.getInformationRecue(), information);
        //Comme recevoir lance emettre cette donction est aussi testé ici. 
        assertEquals(instance.getInformationEmise(), information);
        assertEquals(dest.getInformationRecue(), information);
    }

    /**
     * Test of emettre method, of class TransmetteurLogiqueParfait.
     */
    @Test
    public void testEmettre() throws Exception {
        System.out.println("emettre");
        TransmetteurLogiqueParfait instance = new TransmetteurLogiqueParfait();
        instance.emettre();
        assertEquals(instance.informationEmise, null);
    }

}
