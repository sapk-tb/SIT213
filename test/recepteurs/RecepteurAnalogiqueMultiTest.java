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
        Float[] echNotClean = {1f, 1f};
        Integer[] dt = {2};
        Float[] ar = {0f};
        Information<Float> infRecue = new Information<Float>(echNotClean);
        RecepteurAnalogiqueMulti instance = new RecepteurAnalogiqueMulti("RZ", 3, 1, -1, 1 / 3, 1 / 3, dt, ar);
        Information<Float> expResult = infRecue;
        Information<Float> result = instance.cleanEch(infRecue);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    @Test
    public void testClean() throws Exception {
        System.out.println("cleanEch");
        Float[] echNotClean = {1f, 1f, 1f};
        Integer[] dt = {2};
        Float[] ar = {0.5f};
        Float[] echClean = {1f, 1f, 0.5f};
        Information<Float> infRecue = new Information<Float>(echNotClean);
        RecepteurAnalogiqueMulti instance = new RecepteurAnalogiqueMulti("RZ", 3, 1, -1, 1 / 3, 1 / 3, dt, ar);
        Information<Float> expResult = new Information<Float>(echClean);
        Information<Float> result = instance.cleanEch(infRecue);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
