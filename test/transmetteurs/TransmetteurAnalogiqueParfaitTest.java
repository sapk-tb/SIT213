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
public class TransmetteurAnalogiqueParfaitTest {
    
    public TransmetteurAnalogiqueParfaitTest() {
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
        TransmetteurAnalogiqueParfait instance = new TransmetteurAnalogiqueParfait();
        instance.recevoir(information);

        assertEquals(instance.informationRecue, information);
        //Comme recevoir lance emettre cette donction est aussi testé ici. 
        assertEquals(instance.informationEmise, information);
    }

    /**
     * Test of emettre method, of class TransmetteurAnalogiqueParfait.
     */
    @Test
    public void testEmettre() throws Exception {
        System.out.println("emettre");
        TransmetteurLogiqueParfait instance = new TransmetteurLogiqueParfait();
        instance.emettre();
        assertEquals(instance.informationEmise, null);
    }
    
}
