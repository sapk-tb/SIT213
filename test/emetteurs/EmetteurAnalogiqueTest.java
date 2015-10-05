package emetteurs;

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
import recepteurs.RecepteurAnalogique;

/**
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 * @author Pierrick CHOVELON
 * @author Mélanie CORRE
 */
public class EmetteurAnalogiqueTest {

    private EmetteurAnalogique instance_RZ;
    private float instance_RZ_dutyCycle = 0.5f;
    private int instance_RZ_nbEch = 10;
    private EmetteurAnalogique instance_NRZ;
    private int instance_NRZ_nbEch = 42;
    private EmetteurAnalogique instance_NRZT;
    private int instance_NRZT_nbEch = 14;
    private float instance_NRZT_tempsMontee = 0.33f;
    private RecepteurAnalogique recepteur_NRZ;
    private RecepteurAnalogique recepteur_RZ;
    private RecepteurAnalogique recepteur_NRZT;

    public EmetteurAnalogiqueTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        instance_RZ = new EmetteurAnalogique("RZ", instance_RZ_nbEch, -1, 1, instance_RZ_dutyCycle, 0f);
        instance_NRZ = new EmetteurAnalogique("NRZ", instance_NRZ_nbEch, -2, 2, 0f, 0f);
        instance_NRZT = new EmetteurAnalogique("NRZT", instance_NRZT_nbEch, -3, 1, 0f, instance_NRZT_tempsMontee);

        recepteur_RZ = new RecepteurAnalogique("RZ", instance_RZ_nbEch, -1, 1, instance_RZ_dutyCycle, 0f);
        recepteur_NRZ = new RecepteurAnalogique("NRZ", instance_NRZ_nbEch, -2, 2, 0f, 0f);
        recepteur_NRZT = new RecepteurAnalogique("NRZT", instance_NRZT_nbEch, -3, 1, 0f, instance_NRZT_tempsMontee);

        instance_RZ.connecter(recepteur_RZ);
        instance_NRZ.connecter(recepteur_NRZ);
        instance_NRZT.connecter(recepteur_NRZT);
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

    private Boolean[] getRandomBooleanArray(int n) {
        Boolean[] bits = new Boolean[n];
        Random rnd = new Random();

        for (int i = 0; i < n; i++) {
            bits[i] = rnd.nextBoolean();
        }
        return bits;
    }

    ;
   
    /**
     * Test of constructor of class EmetteurAnalogique.
     */
    @Test
    public void testConstructor() throws Exception {
        //TODO if the args are to be check in constructor
    }
    /**
     * Test of constructor of class EmetteurAnalogique.
     */
    @Test
    public void testInvalidConstructor() throws Exception {
        //TODO if the args are to be check in constructor
    }
    
    /**
     * Test of recevoir method, of class EmetteurAnalogique.
     */
    @Test
    public void testRecevoir() throws Exception {
        System.out.println("Test methode : recevoir");
        Boolean[] bits = getRandomBooleanArray(42);
        Information<Boolean> information = new Information<>(bits);
        instance_RZ.recevoir(information);
        assertEquals(information, instance_RZ.informationRecue);
        assertEquals(information, instance_RZ.getInformationRecue());
        assertEquals("RZ", instance_RZ.getForm());
        assertEquals(instance_RZ.getNbEch() * instance_RZ.getInformationRecue().nbElements(), instance_RZ.informationEmise.nbElements());
        assertEquals(instance_RZ_nbEch, instance_RZ.getNbEch());
        assertEquals(1, instance_RZ.getAmplMax(), 0.0f);
        assertEquals(-1, instance_RZ.getAmplMin(), 0.0f);
        assertEquals(instance_RZ_dutyCycle, instance_RZ.getDutyCycleRZ(), 0.0f);

        //Check duty cycle and niveau
        float niveau[] = {0f, 0f, 0f}; //0 min max
        for (int i = 0; i < instance_RZ.informationEmise.nbElements(); i++) {
            if (instance_RZ.informationEmise.iemeElement(i) == instance_RZ.getAmplMax()) {
                niveau[2]++;
            } else if (instance_RZ.informationEmise.iemeElement(i) == instance_RZ.getAmplMin()) {
                niveau[1]++;
            } else if (instance_RZ.informationEmise.iemeElement(i) == 0) {
                niveau[0]++;
            } else {
                throw new Exception("Niveau de tension invalide : " + instance_RZ.informationEmise.iemeElement(i));
            }
        }
        assertEquals(instance_RZ.getDutyCycleRZ(), (niveau[1] + niveau[2]) / instance_RZ.informationEmise.nbElements(), 0.01f); // Verification du dutyCyle (Avec une petite marge)
        assertEquals(1f - instance_RZ.getDutyCycleRZ(), niveau[0] / instance_RZ.informationEmise.nbElements(), 0.01f); // Verification du dutyCyle (Avec une petite marge)

        //On verifie que les informations recue par le capteur sont bien décotable
        assertEquals(recepteur_RZ.getInformationRecue(), instance_RZ.getInformationEmise());
        assertEquals(recepteur_RZ.getInformationEmise(), instance_RZ.getInformationRecue());

        //Test du nombe de niveau proche (<1%) du nb de niveau global necesaire pour chaque valeur
        assertEquals(niveau[0], (1f - instance_RZ.getDutyCycleRZ())* instance_RZ.getNbEch() * bits.length, 0.01f* instance_RZ.getNbEch() * bits.length);
        assertEquals(niveau[1], getNumberOfIn(Boolean.FALSE, bits) * instance_RZ.getNbEch() *instance_RZ.getDutyCycleRZ(), 0.01f* instance_RZ.getNbEch() * bits.length); 
        assertEquals(niveau[2], getNumberOfIn(Boolean.TRUE, bits) * instance_RZ.getNbEch() *instance_RZ.getDutyCycleRZ(), 0.01f* instance_RZ.getNbEch() * bits.length); 

        Boolean[] bits2 = getRandomBooleanArray(31);
        Information<Boolean> information2 = new Information<>(bits2);
        instance_NRZ.recevoir(information2);
        assertEquals(information2, instance_NRZ.informationRecue);
        assertEquals(information2, instance_NRZ.getInformationRecue());
        assertEquals("NRZ", instance_NRZ.getForm());
        assertEquals(instance_NRZ.getNbEch() * instance_NRZ.getInformationRecue().nbElements(), instance_NRZ.informationEmise.nbElements());

        //Check niveau
        float niveau2[] = {0f, 0f, 0f}; //0 min max
        for (int i = 0; i < instance_NRZ.informationEmise.nbElements(); i++) {
            if (instance_NRZ.informationEmise.iemeElement(i) == instance_NRZ.getAmplMax()) {
                niveau2[2]++;
            } else if (instance_NRZ.informationEmise.iemeElement(i) == instance_NRZ.getAmplMin()) {
                niveau2[1]++;
            } else if (instance_NRZ.informationEmise.iemeElement(i) == 0) {
                niveau2[0]++;
            } else {
                throw new Exception("Niveau de tension invalide : " + instance_NRZ.informationEmise.iemeElement(i));
            }
        }

        //Test du nombe de niveau identique du nb de niveau global necesaire pour chaque valeur (cas NRZ)
        assertEquals(niveau2[0], 0f, 0f); //on ne doit pas être au niveau 0
        assertEquals(niveau2[1], getNumberOfIn(Boolean.FALSE, bits2) * instance_NRZ.getNbEch(), 0f); //on ne doit pas être au niveau 0 
        assertEquals(niveau2[2], getNumberOfIn(Boolean.TRUE, bits2) * instance_NRZ.getNbEch(), 0f); //on ne doit pas être au niveau 0 

        //On verifie que le décodeur peut décoder l'information émise
        assertEquals(recepteur_NRZ.getInformationRecue(), instance_NRZ.getInformationEmise());
        assertEquals(recepteur_NRZ.getInformationEmise(), instance_NRZ.getInformationRecue());

        Information<Boolean> information3 = new Information<>(getRandomBooleanArray(15));
        instance_NRZT.recevoir(information3);
        assertEquals(information3, instance_NRZT.informationRecue);
        assertEquals(information3, instance_NRZT.getInformationRecue());
        assertEquals("NRZT", instance_NRZT.getForm());
        assertEquals(instance_NRZT.getNbEch() * instance_NRZT.getInformationRecue().nbElements(), instance_NRZT.informationEmise.nbElements());
        assertEquals(instance_NRZT_tempsMontee, instance_NRZT.getTmpMontee(), 0.0f);
        //TODO vérification du temps de montée
        
        //On verifie que le décodeur peut décoder l'information émise
        assertEquals(recepteur_NRZT.getInformationRecue(), instance_NRZT.getInformationEmise());
        assertEquals(recepteur_NRZT.getInformationEmise(), instance_NRZT.getInformationRecue());

    }

    /**
     * Test of emettre method, of class EmetteurAnalogique.
     */
    @Test(expected = InformationNonConforme.class)
    public void testEmettreVide() throws Exception {
        System.out.println("Test methode : recevoir");
        instance_RZ.emettre();
    }

}
