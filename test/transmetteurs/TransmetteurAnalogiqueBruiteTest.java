/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 */
public class TransmetteurAnalogiqueBruiteTest {
    
    public TransmetteurAnalogiqueBruiteTest() {
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
     * Test of recevoir method, of class TransmetteurAnalogiqueParfait.
     */
    @Test
    public void testRecevoir() throws Exception {
        System.out.println("Test recevoir");
        Float bits[] = {-1f, 2f, 0f};
        Information<Float> information = new Information<>(bits);
        TransmetteurAnalogiqueBruite instance = new TransmetteurAnalogiqueBruite(0);
        instance.recevoir(information);

        assertEquals(instance.informationRecue, information);
        //Comme recevoir lance emettre cette fonction est aussi testé ici. 
        assertEquals(instance.informationEmise, information);
    }

    /**
     * Test of emettre method, of class TransmetteurAnalogiqueParfait.
     */
    @Test
    public void testEmettre() throws Exception {
        System.out.println("emettre");
        TransmetteurAnalogiqueBruite instance = new TransmetteurAnalogiqueBruite(1);
        instance.emettre();
        //assertEquals(instance.informationEmise.iemeElement(0), null);
        assertTrue(instance.informationEmise.iemeElement(0)>instance.informationEmise.iemeElement(1));
    }
    
}
