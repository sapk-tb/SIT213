package transmetteurs;

import static org.junit.Assert.*;
import information.Information;
import information.InformationNonConforme;

import org.junit.Test;

import recepteurs.RecepteurAnalogique;
import tools.ArrayTool;

public class TransmetteurAnalogiqueBruiteMultiTest{

	@Test//Fonctionnement normal
	public void testConstructeur() throws Exception {
		Integer nbTrajet = 2;
		Integer[] dt = {2, 5};
		Float[] ar = {1f, 1f};
		Float SNR = 10f;
		int seed = 1;
		TransmetteurAnalogiqueBruiteMulti instance = new TransmetteurAnalogiqueBruiteMulti(nbTrajet, dt, ar, SNR, seed);
	}
	
	@Test(expected = Exception.class)//Problème
	public void testConstructeurException() throws Exception {
		Integer nbTrajet = 1;
		Integer[] dt = {2, 5};
		Float[] ar = {1f, 1f};
		Float SNR = 10f;
		int seed = 1;
		TransmetteurAnalogiqueBruiteMulti instance = new TransmetteurAnalogiqueBruiteMulti(nbTrajet, dt, ar, SNR, seed);
	}
	
	/**
     * Test of recevoir method, of class TransmetteurAnalogiqueBruiteMulti.
     */
    @Test//Comparaison de deux émissions de signal avec le même seed
    public void testRecevoirSeed() throws Exception {
        System.out.println("Test recevoir");
        Float bits[] = {1f, 1f, 1f};
        Information<Float> information = new Information<>(bits);
        Integer nbTrajets = 2;
        Integer[] dt = {1,2};
        Float[] ar = {0.5f,0.5f};
        int seed = 1;
        Float SNR = 10f;
        TransmetteurAnalogiqueBruiteMulti instance = new TransmetteurAnalogiqueBruiteMulti(nbTrajets, dt, ar, SNR, seed);
        RecepteurAnalogique recepteurAnalogique = new RecepteurAnalogique("RZ", 3, -2f, 2f, 0.2f, 0.1f);
        instance.connecter(recepteurAnalogique);
        //On vérifie que l'information reçue est bien l'information qu'on a passée en paramètre
        instance.recevoir(information);
        assertEquals(instance.informationRecue, information);
        
        //émission d'un deuxième signal qui va être comparé au premier. Comme c'est le même seed : ils doivent être égaux, même avec du bruit
        TransmetteurAnalogiqueBruiteMulti instance2 = new TransmetteurAnalogiqueBruiteMulti(nbTrajets, dt, ar, SNR, seed);
        instance2.recevoir(information);
        assertEquals(instance.informationRecue, instance.informationRecue);
    }
    
    /**
     * Test of recevoir method, of class TransmetteurAnalogiqueBruiteMulti.
     */
    @Test(expected = InformationNonConforme.class)//SNR null
    public void testRecevoirInformationNonConforme() throws Exception {
        System.out.println("Test recevoir");
        Float bits[] = {1f, 1f, 1f};
        Information<Float> information = new Information<>(bits);
        Integer nbTrajets = 2;
        Integer[] dt = {1,2};
        Float[] ar = {0.5f,0.5f};
        int seed = 1;
        Float SNR = null;//objet du test, on regarde que la méthode retourne bien InformationNonConforme si SNR = null
        TransmetteurAnalogiqueBruiteMulti instance = new TransmetteurAnalogiqueBruiteMulti(nbTrajets, dt, ar, SNR, seed);
        RecepteurAnalogique recepteurAnalogique = new RecepteurAnalogique("RZ", 3, -2f, 2f, 0.2f, 0.1f);
        instance.connecter(recepteurAnalogique);
        instance.recevoir(information);
    }

}
