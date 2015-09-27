package destinations;

import information.Information;
import information.InformationNonConforme;
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
public class DestinationFinaleTest {

    public DestinationFinaleTest() {
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
     * Test of recevoir method, of class DestinationFinale.
     */
    @Test
    public void testRecevoir() {
        System.out.println("Test recevoir");
        Boolean bits[] = {true, false, true};
        Information<Boolean> information = new Information<>(bits);
        DestinationFinale instance = new DestinationFinale();
        try {
            instance.recevoir(information);
        } catch (InformationNonConforme e) {
            fail("The test fail because recevoir didn't accept bool array.");
        }
        Information<Boolean> informationRecue = instance.getInformationRecue();

        assertEquals(informationRecue, information);
    }

}
