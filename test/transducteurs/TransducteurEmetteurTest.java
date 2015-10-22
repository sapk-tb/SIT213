/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transducteurs;

import destinations.DestinationInterface;
import information.Information;

import java.util.LinkedList;
import org.junit.Test;


/**
 *
 * @author Mélanie Corre
 */
public class TransducteurEmetteurTest {
    Information<R> informationRecue;
    Information<E> informationEmise;
    Information <Boolean>  informationGeneree;
    

        public TransducteurEmetteurTest() {
            
    }
        public void testGenererInformation(){
            System.out.println("Test methode : emettre");
            informationGeneree = new Information<>(informationRecue.nbElements() * 3);
            /* TODO tester :
            La classe reçoit une information de taille N avec des true et des false.
Elle génère une information de taille 3*N qui contient des true et false.
Ex :
Taille N = 2.
Reçue : {true, false}
Émise : {true, false, true, false, true, false}
            */
        }
    
}
