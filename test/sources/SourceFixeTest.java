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
    public void testConstructor() {
        System.out.println("Test Constuctor SourceFixe");
        String mess = "01011";
        SourceFixe instance = new SourceFixe(mess);

        assertEquals(instance.informationGeneree.nbElements(), mess.length());
        Boolean bites[] = {false, true, false, true, true};
        Information<Boolean> information = new Information<>(bites);
        assertEquals(instance.informationGeneree, information);

        String mess2 = "1101011";
        SourceFixe instance2 = new SourceFixe(mess2);

        assertEquals(instance2.informationGeneree.nbElements(), mess2.length());
        Boolean bites2[] = {true, true, false, true, false, true, true};
        Information<Boolean> information2 = new Information<>(bites2);
        assertEquals(instance2.informationGeneree, information2);
    }

}
