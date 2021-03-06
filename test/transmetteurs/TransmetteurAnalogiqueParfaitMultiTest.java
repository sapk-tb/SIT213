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
import recepteurs.RecepteurAnalogiqueMulti;
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
        Double bits[] = {1.0, 1.0, 1.0};
        Information<Double> information = new Information<>(bits);
        int nbTrajets = 2;
        Integer[] dt = {1,2};
        Double[] ar = {0.5,0.5};
        TransmetteurAnalogiqueParfaitMulti instance = new TransmetteurAnalogiqueParfaitMulti(dt, ar );
        RecepteurAnalogiqueMulti recepteurAnalogiqueMulti = new RecepteurAnalogiqueMulti("RZ", 1, -2f, 2f, 0.2f, 0.1f, dt, ar);
        instance.connecter(recepteurAnalogiqueMulti);
        instance.recevoir(information);
        assertEquals(instance.informationRecue, information);
        
        //construction d'une information qui est la somme de l'information + 2 trajets différents

        Information<Double> informationTotale = new Information<>(bits);
        Double bitsT1[] = {0.0, 0.5, 0.5, 0.5, 0.0};//Trajet1 : * 0.5
        Double bitsT2[] = {0.0, 0.0, 0.5, 0.5, 0.5};//Trajet2 : * 0.5
        Information<Double> informationT1 = new Information<>(bitsT1);
        Information<Double> informationT2 = new Information<>(bitsT2);

        informationTotale = ArrayTool.sumArrays(informationTotale, informationT1);
        informationTotale = ArrayTool.sumArrays(informationTotale, informationT2);     
        
        //Comme recevoir lance emettre cette fonction est aussi testée ici. 
        assertEquals(instance.informationEmise, informationTotale);
        //Le récepeteur marche bien pour retrouver l'information totale. 
        //TODO tester s'il peut retrouver le signal initiale qui est dans l'informationTotale
        Boolean tab[] = {true, true, true};
        Information<Boolean> inf = new Information<Boolean>(tab);
        assertEquals(inf, recepteurAnalogiqueMulti.getInformationEmise());
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
