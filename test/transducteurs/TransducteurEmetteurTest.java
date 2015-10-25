/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transducteurs;

import static org.junit.Assert.*;
import destinations.DestinationInterface;
import information.Information;

import java.util.LinkedList;

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
            //informationRecue={true, false};
            Boolean[] contenu={true, false, true, false, true, false};
            Information<Boolean> informationEmise=new Information<Boolean>();
            informationEmise.add(true);
            informationEmise.add(false);
            informationEmise.add(true);
            informationEmise.add(false);
            informationEmise.add(true);
            informationEmise.add(false);
            //Information<Boolean> informationEmise={true, false, true, false, true, false};
            Information <Boolean>  informationGeneree;
            int taille=2;
            int nbElements = informationRecue.nbElements();
            System.out.println("Test methode : emettre");
            informationGeneree = new Information<>(nbElements * 3);
            TransducteurEmetteur instance= new TransducteurEmetteur();
            instance.emettre();
            assertEquals(informationEmise, informationGeneree);
            //assertEquals(this.informationEmise, informationGeneree);
        }
    
}
