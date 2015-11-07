/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recepteurs;

import information.Information;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sapk
 */
public class RecepteurAnalogiqueMultiTest {

    public RecepteurAnalogiqueMultiTest() {
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
     * Test of cleanEch method, of class RecepteurAnalogiqueMulti.
     */
    @Test
    public void testCleanEchAREqual0() throws Exception {
        System.out.println("cleanEch");
        Double[] echNotClean = {1.0, 1.0};
        Integer[] dt = {2};
        Double[] ar = {0.0};
        Information<Double> infRecue = new Information<Double>(echNotClean);
        RecepteurAnalogiqueMulti instance = new RecepteurAnalogiqueMulti("RZ", 3, 1, -1, 1 / 3, 1 / 3, dt, ar);
        Information<Double> expResult = infRecue;
        Information<Double> result = instance.cleanEch(infRecue);
        assertEquals(expResult, result);
    }

    @Test
    public void testClean() throws Exception {
        System.out.println("cleanEch");
        Integer[] dt = {3};
        Double[] ar = {Math.ceil(Math.random() * 100) / 100}; //On limite la precisison car java se plante dans certains arrondi entre double.
        Double[] echClean = {1.0, 1.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0};
        Double[] echNotClean = {1.0, 1.0, 0.0, 0.0 + (ar[0] * echClean[0]), 1.0 + (ar[0] * echClean[1]), 1.0 + (ar[0] * echClean[2]), 1.0 + (ar[0] * echClean[3]), 1.0 + (ar[0] * echClean[4]), 0.0 + (ar[0] * echClean[5]), 0.0 + (ar[0] * echClean[6]), 0.0 + (ar[0] * echClean[7]), 0.0 + (ar[0] * echClean[8]), (ar[0] * echClean[9]), (ar[0] * echClean[10]), (ar[0] * echClean[11])};

//Double[] echNotClean =  {1.0, 1.0, 0.0, 0.5, 1.5, 1.0, 1.0, 1.5, 0.5, 0.5, 0.5, 0.0, 0.0, 0.0, 0.0};
        Information<Double> infRecue = new Information<Double>(echNotClean);
        RecepteurAnalogiqueMulti instance = new RecepteurAnalogiqueMulti("NRZ", 2, 0.0, 1.1, 1 / 3, 1 / 3, dt, ar);
        Information<Double> result = instance.cleanEch(infRecue);

        for (int i = 0; i < echClean.length; i++) {
            Double echExp = echClean[i];
            assertEquals(echExp, result.iemeElement(i),0.01);
        }
    }

}
