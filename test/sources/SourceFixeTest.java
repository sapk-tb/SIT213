package sources;

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
public class SourceFixeTest {

    public SourceFixeTest() {
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
    public void testConstructeur() {
        System.out.println("Test Constucteur SourceFixe");
        String mess = "01011";
        SourceFixe instance = new SourceFixe(mess);

        assertEquals(instance.informationGeneree.nbElements(), mess.length());
        Boolean bits[] = {false, true, false, true, true};
        Information<Boolean> information = new Information<>(bits);
        assertEquals(instance.informationGeneree, information);

        String mess2 = "1101011";
        SourceFixe instance2 = new SourceFixe(mess2);

        assertEquals(instance2.informationGeneree.nbElements(), mess2.length());
        Boolean bits2[] = {true, true, false, true, false, true, true};
        Information<Boolean> information2 = new Information<>(bits2);
        assertEquals(instance2.informationGeneree, information2);
    }

}
