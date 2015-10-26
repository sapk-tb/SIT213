/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transducteurs;

import static org.junit.Assert.*;
import information.Information;


import org.junit.Test;


/**
 *
 * @author Mélanie Corre
 */
public class TransducteurEmetteurTest {

    

        public TransducteurEmetteurTest() {
            
    }
        @Test
        public void testGenererInformation() throws Exception{
            Information<Boolean> informationRecue= new Information<Boolean>();
            informationRecue.add(true);
            informationRecue.add(false);
            Information<Boolean> informationEmise=new Information<Boolean>();
            informationEmise.add(true);
            informationEmise.add(false);
            informationEmise.add(true);
            informationEmise.add(false);
            informationEmise.add(true);
            informationEmise.add(false);
            TransducteurEmetteur instance= new TransducteurEmetteur();
            instance.informationEmise=informationEmise;
            System.out.println("Test methode : recevoir");
            instance.recevoir(informationRecue);
            assertEquals(informationEmise, instance.informationEmise);
        }
    
}
