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
     * Test of recevoir method, of class TransmetteurAnalogiqueBruite.
     * @throws java.lang.Exception
     */
    @Test
    public void testRecevoir() throws Exception {
        System.out.println("Test recevoir");
        Double bits[] = {-1.0, 2.0, 0.0};
        Information<Double> information = new Information<>(bits);
        TransmetteurAnalogiqueBruite instance = new TransmetteurAnalogiqueBruite(null);
        RecepteurAnalogique recepteurAnalogique = new RecepteurAnalogique("RZ", 3, -2f, 2f, 0.2f, 0.1f);
        instance.connecter(recepteurAnalogique);
        instance.recevoir(information);

        assertEquals(instance.informationRecue, information);
        //Comme recevoir lance emettre cette fonction est aussi testé ici. 
        assertEquals(instance.informationEmise, information);
        assertEquals(information, recepteurAnalogique.getInformationRecue());
    }
//TODO tester répartion gaussienne

    /**
     * Test of recevoir method, of class TransmetteurAnalogiqueBruite.
     */
    @Test(expected = InformationNonConforme.class)//Aucune infos passée en parem
    public void testRecevoirNonConforme() throws Exception {
        System.out.println("Test recevoir - Non conforme");
        TransmetteurAnalogiqueBruite instance = new TransmetteurAnalogiqueBruite(null);
        instance.recevoir(null);
    }

    /**
     * Test of emettre method, of class TransmetteurAnalogiqueBruite.
     */
    @Test(expected = NullPointerException.class) //on lui donne rien à émettre
    public void testEmettre() throws Exception {
        System.out.println("emettre - null pointer");
        TransmetteurAnalogiqueBruite instance = new TransmetteurAnalogiqueBruite(1.0);
        instance.emettre();
        assertEquals(instance.informationEmise.iemeElement(0), null);
        //assertTrue(instance.informationEmise.iemeElement(0)>instance.informationEmise.iemeElement(1));
    }

}
