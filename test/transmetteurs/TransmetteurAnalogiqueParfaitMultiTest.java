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
import tools.ArrayTool;


/**
 *
 * @author Antoine GIRARD
 */
public class TransmetteurAnalogiqueParfaitMultiTest {
    
    public TransmetteurAnalogiqueParfaitMultiTest() {
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
     * Test of recevoir method, of class TransmetteurAnalogiqueParfaitMulti.
     */
    @Test
    public void testRecevoir() throws Exception {
        System.out.println("Test recevoir");
        Float bits[] = {1f, 1f, 1f};
        Information<Float> information = new Information<>(bits);
        int nbTrajets = 2;
        Integer[] dt = {1,2};
        Float[] ar = {0.5f,0.5f};
        TransmetteurAnalogiqueParfaitMulti instance = new TransmetteurAnalogiqueParfaitMulti(nbTrajets, dt, ar );
        RecepteurAnalogique recepteurAnalogique = new RecepteurAnalogique("RZ", 3, -2f, 2f, 0.2f, 0.1f);
        instance.connecter(recepteurAnalogique);
        instance.recevoir(information);
        assertEquals(instance.informationRecue, information);
        
        //construction d'une information qui est la somme de l'information + 2 trajets différents

        Information<Float> informationTotale = new Information<>(bits);
        Float bitsT1[] = {0.0f, 0.5f, 0.5f, 0.5f, 0.0f};//Trajet1 : *0.5
        Float bitsT2[] = {0.0f, 0.0f, 0.5f, 0.5f, 0.5f};//Trajet2 : *0.5
        Information<Float> informationT1 = new Information<>(bitsT1);
        Information<Float> informationT2 = new Information<>(bitsT2);

        informationTotale = ArrayTool.sumArrays(informationTotale, informationT1);
        informationTotale = ArrayTool.sumArrays(informationTotale, informationT2);     
        
        System.out.println("_________");
        System.out.println(informationTotale.toString());
        
        //Comme recevoir lance emettre cette fonction est aussi testée ici. 
        assertEquals(instance.informationEmise, informationTotale);
        //Le récepeteur marche bien pour retrouver l'information totale. 
        //TODO tester s'il peut retrouver le signal initiale qui est dans l'informationTotale
        //assertEquals(recepteurAnalogique.getInformationRecue(), information);
    }

    /**
     * Test of emettre method, of class TransmetteurAnalogiqueParfait.
     */
    @Test
    public void testEmettre() throws Exception {
        System.out.println("emettre");
        TransmetteurAnalogiqueParfait instance = new TransmetteurAnalogiqueParfait();
        instance.emettre();
        assertEquals(instance.informationEmise, null);
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
