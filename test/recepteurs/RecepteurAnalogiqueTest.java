/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recepteurs;

import emetteurs.EmetteurAnalogique;
import information.Information;
import information.InformationNonConforme;
import java.util.Objects;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Antoine GIRARD
 */
public class RecepteurAnalogiqueTest {

    private RecepteurAnalogique instance_RZ;
    private float instance_RZ_dutyCycle = 0.5f;
    private int instance_RZ_nbEch = 10;
    private RecepteurAnalogique instance_NRZ;
    private int instance_NRZ_nbEch = 42;
    private RecepteurAnalogique instance_NRZT;
    private int instance_NRZT_nbEch = 44;
    private float instance_NRZT_tempsMontee = 0.33f;
    private EmetteurAnalogique emetteur_RZ;
    private EmetteurAnalogique emetteur_NRZ;
    private EmetteurAnalogique emetteur_NRZT;

    public RecepteurAnalogiqueTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        instance_RZ = new RecepteurAnalogique("RZ", instance_RZ_nbEch, -1, 1, instance_RZ_dutyCycle, 0f);
        instance_NRZ = new RecepteurAnalogique("NRZ", instance_NRZ_nbEch, -2, 2, 0f, 0f);
        instance_NRZT = new RecepteurAnalogique("NRZT", instance_NRZT_nbEch, -3, 1, 0f, instance_NRZT_tempsMontee);

        emetteur_RZ = new EmetteurAnalogique("RZ", instance_RZ_nbEch, -1, 1, instance_RZ_dutyCycle, 0f);
        emetteur_NRZ = new EmetteurAnalogique("NRZ", instance_NRZ_nbEch, -2, 2, 0f, 0f);
        emetteur_NRZT = new EmetteurAnalogique("NRZT", instance_NRZT_nbEch, -3, 1, 0f, instance_NRZT_tempsMontee);

        instance_RZ.connecter(emetteur_RZ);
        instance_NRZ.connecter(emetteur_NRZ);
        instance_NRZT.connecter(emetteur_NRZT);
    }

    @After
    public void tearDown() {
    }

    private int getNumberOfIn(Boolean n, Boolean[] bits) {
        int total = 0;
        for (Boolean bit : bits) {
            if (Objects.equals(bit, n)) {
                total++;
            }
        }
        return total;
    }

    private Float[] getRandomFloatArray(String form, int nbSym, int nbEch, float min, float max, float dutyCylce, float tempsMontee) {
        Float[] bits = new Float[nbSym * nbEch];
        Random rnd = new Random();

        switch (form) {
            case "RZ":
                for (int i = 0; i < nbSym; i++) {
                    Boolean lvl = rnd.nextBoolean();
                    for (int n = 0; n < nbEch * dutyCylce; n++) {
                        bits[i * nbEch + n] = (lvl) ? max : min;
                    }
                    for (int n = (int) Math.ceil(nbEch * dutyCylce); n < nbEch; n++) {
                        bits[i * nbEch + n] = (lvl) ? max : min;
                    }
                }
                break;
            case "NRZ":
                for (int i = 0; i < nbSym; i++) {
                    Boolean lvl = rnd.nextBoolean();
                    for (int n = 0; n < nbEch; n++) {
                        bits[i * nbEch + n] = (lvl) ? max : min;
                    }
                }
                break;
            case "NRZT":
                float diff_max = (float) (Math.abs(max - min) / Math.floor(nbEch * tempsMontee));
                
                for (int i = 0; i < nbSym; i++) {
                    Boolean lvl = rnd.nextBoolean();
                    float niv_prec = 0f;
                    for (int n = 0; n < nbEch; n++) {
                        float niv = (lvl) ? max : min;
                        if (Math.abs(niv_prec - niv) > diff_max) {
                            if(niv> niv_prec){
                                bits[i * nbEch + n] = niv_prec + diff_max; //On monte
                            }else{
                                bits[i * nbEch + n] = niv_prec - diff_max; //on descend
                            }
                        }else{
                            bits[i * nbEch + n] = niv;
                        }
                        //                            bits[i * nbEch + n] = 
                        niv_prec = bits[i * nbEch + n];
                    }
                }
                break;

        }
        return bits;
    }

    private Float[] getRandomFloatArray(int n) {
        Float[] bits = new Float[n];
        Random rnd = new Random();

        for (int i = 0; i < n; i++) {
            bits[i] = rnd.nextFloat();
        }
        return bits;
    }

    private Boolean[] getRandomBooleanArray(int n) {
        Boolean[] bits = new Boolean[n];
        Random rnd = new Random();

        for (int i = 0; i < n; i++) {
            bits[i] = rnd.nextBoolean();
        }
        return bits;
    }

    /**
     * Test of recevoir for RZ method, of class EmetteurAnalogique.
     */
    @Test
    public void testRecevoirRZ() throws Exception {
        System.out.println("Test methode : recevoir");
        //Float[] bits = getRandomFloatArray(instance_RZ_nbEch * 10);
        Float[] bits = getRandomFloatArray("RZ", 10, instance_RZ_nbEch, -1, 1, instance_RZ_dutyCycle, 0f);

        Information<Float> information = new Information<>(bits);
        instance_RZ.recevoir(information);
        assertEquals(information, instance_RZ.informationRecue);
        assertEquals(information, instance_RZ.getInformationRecue());

        assertEquals("RZ", instance_RZ.getForm());
        assertEquals(instance_RZ_nbEch, instance_RZ.getNbEch());
        assertEquals(1, instance_RZ.getAmplMax(), 0.0f);
        assertEquals(-1, instance_RZ.getAmplMin(), 0.0f);
        assertEquals(instance_RZ_dutyCycle, instance_RZ.getDutyCycleRZ(), 0.0f);

        assertEquals(instance_RZ.getInformationRecue().nbElements() / instance_RZ.getNbEch(), instance_RZ.informationEmise.nbElements());

        //TODO
        //On verifie que les informations recue par le capteur sont bien décotable
        //assertEquals(emetteur_RZ.getInformationRecue(), instance_RZ.getInformationEmise());
        //assertEquals(emetteur_RZ.getInformationEmise(), instance_RZ.getInformationRecue());
    }

    /**
     * Test of recevoir for NRZ method, of class EmetteurAnalogique.
     */
    @Test
    public void testRecevoirNRZ() throws Exception {
        System.out.println("Test methode : recevoir");
        //Float[] bits = getRandomFloatArray(instance_NRZ_nbEch * 10);
        Float[] bits = getRandomFloatArray("NRZ", 10, instance_NRZ_nbEch, -2, 2, 0f, 0f);
        Information<Float> information = new Information<>(bits);
        instance_NRZ.recevoir(information);
        assertEquals(information, instance_NRZ.informationRecue);
        assertEquals(information, instance_NRZ.getInformationRecue());

        assertEquals("NRZ", instance_NRZ.getForm());
        assertEquals(instance_NRZ_nbEch, instance_NRZ.getNbEch());
        assertEquals(2, instance_NRZ.getAmplMax(), 0.0f);
        assertEquals(-2, instance_NRZ.getAmplMin(), 0.0f);

        assertEquals(instance_NRZ.getInformationRecue().nbElements() / instance_NRZ.getNbEch(), instance_NRZ.informationEmise.nbElements());

        //On verifie que les informations recue par le capteur sont bien décotable
        assertEquals(emetteur_NRZ.getInformationRecue(), instance_NRZ.getInformationEmise());
        assertEquals(emetteur_NRZ.getInformationEmise(), instance_NRZ.getInformationRecue());
    }

    /**
     * Test of recevoir for NRZT method, of class EmetteurAnalogique.
     */
    @Test
    public void testRecevoirNRZT() throws Exception {
        System.out.println("Test methode : recevoir");
        //Float[] bits = getRandomFloatArray(instance_NRZT_nbEch * 10); //TODO generate real waveform
        Float[] bits = getRandomFloatArray("NRZT", 10, instance_NRZT_nbEch, -3, 1, 0f, instance_NRZT_tempsMontee);
        Information<Float> information = new Information<>(bits);
        instance_NRZT.recevoir(information);
        assertEquals(information, instance_NRZT.informationRecue);
        assertEquals(information, instance_NRZT.getInformationRecue());

        assertEquals("NRZT", instance_NRZT.getForm());
        assertEquals(instance_NRZT_nbEch, instance_NRZT.getNbEch());
        assertEquals(1, instance_NRZT.getAmplMax(), 0.0f);
        assertEquals(-3, instance_NRZT.getAmplMin(), 0.0f);
        assertEquals(instance_NRZT_tempsMontee, instance_NRZT.getTmpMontee(), 0.0f);

        assertEquals(instance_NRZT.getInformationRecue().nbElements() / instance_NRZT.getNbEch(), instance_NRZT.informationEmise.nbElements());

        //On verifie que les informations recue par le capteur sont bien décotable
        assertEquals(emetteur_NRZT.getInformationRecue(), instance_NRZT.getInformationEmise());
        //assertEquals(emetteur_NRZT.getInformationEmise(), instance_NRZT.getInformationRecue());
                //assertArrayEquals(instance_NRZT.getInformationRecue(), emetteur_NRZT.getInformationEmise(), 0.01f);
        //TODO
        /*
        for (int i = 0; i < emetteur_NRZT.getInformationEmise().nbElements(); i++) {
            assertEquals(instance_NRZT.getInformationRecue().iemeElement(i), emetteur_NRZT.getInformationEmise().iemeElement(i), 0.01f);
        }
        */
        
    }

    /**
     * Test of emettre method, of class EmetteurAnalogique.
     */
    @Test(expected = InformationNonConforme.class)
    public void testEmettreVide() throws InformationNonConforme {
        System.out.println("Test methode : emettre");
        instance_RZ.emettre();
    }

}
