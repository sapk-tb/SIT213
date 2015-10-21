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
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    @Test
    public void testClean() throws Exception {
        System.out.println("cleanEch");
        Double[] echNotClean = {1.0, 1.0, 1.0};
        Integer[] dt = {2};
        Double[] ar = {0.5};
        Double[] echClean = {1.0, 1.0, 0.5};
        Information<Double> infRecue = new Information<Double>(echNotClean);
        RecepteurAnalogiqueMulti instance = new RecepteurAnalogiqueMulti("RZ", 3, 1, -1, 1 / 3, 1 / 3, dt, ar);
        Information<Double> expResult = new Information<Double>(echClean);
        Information<Double> result = instance.cleanEch(infRecue);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
