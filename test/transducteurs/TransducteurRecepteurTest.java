package transducteurs;

import static org.junit.Assert.*;

import org.junit.Test;

import information.Information;

/**
*
* @author Mélanie Corre
*/

public class TransducteurRecepteurTest {

	@Test
	public void testEmettre() {
		
	}

	@Test
	public void testTransducteurRecepteur() {
		
	}
/*
 * Test de la méthode recevoirInformation
 */
	@Test
	public void testRecevoirInformationOfBoolean() throws Exception {
        Information<Boolean> informationEmise= new Information<Boolean>();
        informationEmise.add(true);
        informationEmise.add(false);
        Information<Boolean> informationRecue=new Information<Boolean>();
        informationRecue.add(true);
        informationRecue.add(false);
        informationRecue.add(true);
        informationRecue.add(false);
        informationRecue.add(true);
        informationRecue.add(false);
        TransducteurRecepteur instance = new TransducteurRecepteur();
        System.out.println("Test methode : recevoir");
        instance.recevoir(informationRecue);
        assertEquals(instance.informationEmise, informationEmise);
	}

}
