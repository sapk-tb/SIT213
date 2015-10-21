package transmetteurs;

import static org.junit.Assert.*;
import information.Information;
import information.InformationNonConforme;

import org.junit.Test;

import recepteurs.RecepteurAnalogique;

public class TransmetteurAnalogiqueBruiteMultiTest {

    @Test//Fonctionnement normal
    public void testConstructeur() throws Exception {
        System.out.println("Test Constructeur");
        Integer nbTrajet = 2;
        Integer[] dt = {2, 5};
        Double[] ar = {1.0, 1.0};
        Double SNR = 10.0;
        int seed = 1;
        TransmetteurAnalogiqueBruiteMulti instance = new TransmetteurAnalogiqueBruiteMulti(dt, ar, SNR, seed);
    }

    @Test(expected = Exception.class)//Problème
    public void testConstructeurException() throws Exception {
        System.out.println("Test ConstructeurException");
        Integer nbTrajet = 1;
        Integer[] dt = {2, 5};
        Double[] ar = {1.0};
        Double SNR = 10.0;
        int seed = 1;
        TransmetteurAnalogiqueBruiteMulti instance = new TransmetteurAnalogiqueBruiteMulti(dt, ar, SNR, seed);
    }

    /**
     * Test of recevoir method, of class TransmetteurAnalogiqueBruiteMulti.
     *
     * @throws java.lang.Exception
     */
    @Test//Comparaison de deux émissions de signal avec le même seed
    public void testRecevoirSeed() throws Exception {
        System.out.println("Test RecevoirSeed");
        Double bits[] = {1.0, 1.0, 1.0};
        Information<Double> information = new Information<>(bits);
        Integer nbTrajets = 2;
        Integer[] dt = {1, 2};
        Double[] ar = {0.5, 0.5};
        int seed = 1;
        Double SNR = 10.0;
        TransmetteurAnalogiqueBruiteMulti instance = new TransmetteurAnalogiqueBruiteMulti(dt, ar, SNR, seed);
        RecepteurAnalogique recepteurAnalogique = new RecepteurAnalogique("RZ", 3, -2f, 2f, 0.2f, 0.1f);
        instance.connecter(recepteurAnalogique);
        //On vérifie que l'information reçue est bien l'information qu'on a passée en paramètre
        instance.recevoir(information);
        assertEquals(instance.informationRecue, information);

        //émission d'un deuxième signal qui va être comparé au premier. Comme c'est le même seed : ils doivent être égaux, même avec du bruit
        TransmetteurAnalogiqueBruiteMulti instance2 = new TransmetteurAnalogiqueBruiteMulti(dt, ar, SNR, seed);
        instance2.recevoir(information);
        assertEquals(instance.informationRecue, instance.informationRecue);
    }

    /**
     * Test of recevoir method, of class TransmetteurAnalogiqueBruiteMulti.
     *
     * @throws java.lang.Exception
     */
    @Test(expected = InformationNonConforme.class)//SNR null
    public void testRecevoirInformationNonConforme() throws Exception {
        System.out.println("Test testRecevoirInformationNonConforme");
        Information<Double> information = null; //Object du test
        Integer[] dt = {1, 2};
        Double[] ar = {0.5, 0.5};
        int seed = 1;
        Double SNR = 10.0;
        TransmetteurAnalogiqueBruiteMulti instance = new TransmetteurAnalogiqueBruiteMulti(dt, ar, SNR, seed);
        instance.recevoir(information);
    }
//TODO verifier que l'onaccepte bien un snr null
}
