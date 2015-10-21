package sources;

import tools.Tool;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import tools.Statistic;

/**
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 * @author Mélanie CORRE
 * @author Pierrick CHOVELON
 */
public class SourceBruitGaussienTest {

    double margeErreur = 0.1f;

    public SourceBruitGaussienTest() {
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
    public void testConstructeur1() {
        System.out.println("Test Constucteur2 SourceBruitGaussien");
        int nbEch = 100;
        SourceBruitGaussien instance = new SourceBruitGaussien(nbEch);
        assertEquals(instance.informationGeneree.nbElements(), nbEch);
    }

    @Test
    public void testConstructeur2() {
        System.out.println("Test Constucteur2 SourceBruitGaussien");
        int nbEch = 1000000;
        double puissance = 1000;
        SourceBruitGaussien instance = new SourceBruitGaussien(nbEch, puissance);
        assertEquals(instance.informationGeneree.nbElements(), nbEch);
        assertEquals(Tool.getPuissance(instance.informationGeneree), puissance, margeErreur * puissance);

    }

    @Test
    public void testRepetitionAvecSeed() {
        System.out.println("Test Répétition SourceBruitGaussien");
        int nbEch = 100000;
        double puissance = 10;
        int seed = 42;
        SourceBruitGaussien instance1 = new SourceBruitGaussien(nbEch, puissance, seed);
        SourceBruitGaussien instance2 = new SourceBruitGaussien(nbEch, puissance, seed);
        assertEquals(instance1.informationGeneree.nbElements(), nbEch);
        assertEquals(instance2.informationGeneree.nbElements(), nbEch);
        assertEquals(Tool.getPuissance(instance1.informationGeneree), puissance, margeErreur * puissance);
        assertEquals(Tool.getPuissance(instance2.informationGeneree), puissance, margeErreur * puissance);
        assertEquals(instance1.informationGeneree,instance2.informationGeneree);

    }

    @Test
    public void testRepartion() {
        System.out.println("Test Repartition SourceBruitGaussien");
        int nbEch = 10000000;
        double puissance = 10;
        SourceBruitGaussien instance = new SourceBruitGaussien(nbEch, puissance);
        assertEquals(instance.informationGeneree.nbElements(), nbEch);
        assertEquals(Tool.getPuissance(instance.informationGeneree), puissance, margeErreur * puissance);
        Double[] values = new Double[nbEch];
        instance.informationGeneree.toArray(values);
        Statistic stat = new Statistic(values);
        assertEquals(stat.getMean(), 0.0, margeErreur); //check moyennes
        assertEquals(stat.getVariance(), puissance, margeErreur * puissance); //check variance

        Double min = stat.getMin();
        Double max = stat.getMax();
        int nbInterval = 100000;
        Double interval = (max - min) / nbInterval;
        double[] repartition = new double[nbInterval];
        //System.out.println("Min : " + min + " Max : " + max + " Interval : " + interval);
        for (Double val : values) {
            int index = (int) ((val - min) * 1 / interval);
            //System.out.println("Index : " + index);
            for (int i = 0; i < index; i++) {
                repartition[i]++;
            }
        }
        System.out.println("à 50% : " + repartition[nbInterval / 2 + 1] + " >= " + 0.5f * nbEch);
        assertTrue(repartition[nbInterval / 2 + 1] >= (0.50f - margeErreur * 2) * nbEch); // on vérifie au centre avec une marge de 5% 
        //TODO add more value to test
    }
}
