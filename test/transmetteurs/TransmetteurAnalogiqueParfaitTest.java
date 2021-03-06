/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transmetteurs;

import information.Information;
import information.InformationNonConforme;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import recepteurs.RecepteurAnalogique;

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
        Double bits[] = {-1.0, 2.0, 0.0};
        Information<Double> information = new Information<>(bits);
        TransmetteurAnalogiqueParfait instance = new TransmetteurAnalogiqueParfait();
        RecepteurAnalogique recepteurAnalogique = new RecepteurAnalogique("RZ", 3, -2f, 2f, 0.2f, 0.1f);
        instance.connecter(recepteurAnalogique);
        instance.recevoir(information);

        assertEquals(instance.informationRecue, information);
        //Comme recevoir lance emettre cette donction est aussi testé ici. 
        assertEquals(instance.informationEmise, information);
        assertEquals(recepteurAnalogique.getInformationRecue(), information);
    }

    /**
     * Test of class TransmetteurAnalogiqueParfait.
     */
    @Test
    public void testReceptionVide() throws Exception {
        System.out.println("emettre");
        TransmetteurAnalogiqueParfait instance = new TransmetteurAnalogiqueParfait();
        assertEquals(instance.informationRecue, null);
    }

    /**
     * Test of recevoir method, of class TransmetteurAnalogiqueParfait.
     */
    @Test(expected = InformationNonConforme.class)//Aucune infos passée en parem
    public void testRecevoirNonConforme() throws Exception {
        System.out.println("Test recevoir - Non conforme");
        TransmetteurAnalogiqueParfait instance = new TransmetteurAnalogiqueParfait();
        instance.recevoir(null);
    }
}
