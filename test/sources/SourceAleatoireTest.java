package sources;

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
public class SourceAleatoireTest {

    public SourceAleatoireTest() {
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
        System.out.println("Test Constucteur SourceAleatoire");
        int mess_length = 1024;
        SourceAleatoire instance = new SourceAleatoire(mess_length);

        assertEquals(instance.informationGeneree.nbElements(), mess_length);

        int mess_with_seed_length = 300;
        int mess_with_seed_seed = 42;
        SourceAleatoire instance_with_seed1 = new SourceAleatoire(mess_with_seed_length, mess_with_seed_seed);
        assertEquals(instance_with_seed1.informationGeneree.nbElements(), mess_with_seed_length);
        SourceAleatoire instance_with_seed2 = new SourceAleatoire(mess_with_seed_length, mess_with_seed_seed);
        assertEquals(instance_with_seed2.informationGeneree.nbElements(), mess_with_seed_length);
        
        assertEquals(instance_with_seed2.informationGeneree, instance_with_seed1.informationGeneree);

    }

}
