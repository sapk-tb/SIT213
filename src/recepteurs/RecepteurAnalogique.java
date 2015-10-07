package recepteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

/**
 * Classe d'un composant recepteur d'informations dont les élèments sont de type
 * RecepteurAnalogique qui hérite de la classe Recepteur
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 * @author Pierrick CHOVELON
 * @author Mélanie CORRE
 */
public class RecepteurAnalogique extends Recepteur<Float, Boolean> {

    private final String form;
    private final int nbEch;
    private final float amplMin;
    private final float amplMax;

    public String getForm() {
        return form;
    }

    public int getNbEch() {
        return nbEch;
    }

    public float getAmplMin() {
        return amplMin;
    }

    public float getAmplMax() {
        return amplMax;
    }

    public float getDutyCycleRZ() {
        return dutyCycleRZ;
    }

    public float getTmpMontee() {
        return tmpMontee;
    }
    private final float dutyCycleRZ;
    private final float tmpMontee;

    /**
     * Constructeur du récepteur analogique
     *
     * @param form Forme du signal à recevoir
     * @param nbEch Nombre d'écahntillon par symbole
     * @param amplMin Amplitude pour la valeur binaire 0
     * @param amplMax Amplitude pour la valeur binaire 1
     * @param dutyCycleRZ Dutycycle à utiliser dans le cadre d'une forme RZ
     * @param tmpMontee Temps de montée à respecté dans le cadre d'une forme
     * NRZT
     */
    public RecepteurAnalogique(String form, int nbEch, float amplMin, float amplMax, float dutyCycleRZ, float tmpMontee) {
        super();
        //TODO check validity of args
        this.form = form;
        this.nbEch = nbEch;
        this.amplMin = amplMin;
        this.amplMax = amplMax;
        this.dutyCycleRZ = dutyCycleRZ;
        this.tmpMontee = tmpMontee;
    }

    /**
     * reçoit une information. Cette méthode, en fin d'exécution, appelle la
     * méthode emettre.
     *
     * @param information l'information reçue
     */
    @Override
    public void recevoir(Information<Float> information) throws InformationNonConforme {
        this.informationRecue = information;
        emettre();
    }

    /**
     * émet l'information construite par l'emetteur
     */
    @Override
    public void emettre() throws InformationNonConforme {
        if (informationRecue == null) {
            throw new InformationNonConforme("informationRecue == null");
        }

        int nbEchTotal = informationRecue.nbElements();
        int nbSymbole = nbEchTotal / nbEch;
        Information<Boolean> informationAEmettre = new Information<Boolean>(nbSymbole);
        //Float allEch[] = new Float[nbEchTotal];
        float total[] = new float[nbSymbole];

//        informationRecue.toArray(allEch);
        /*
         * Calcul de la somme pour chaque échantillon
         */
        for (int i = 0; i < nbEchTotal; i++) {
            //total[(int) i / nbEch] += allEch[i];
            total[(int) i / nbEch] += informationRecue.iemeElement(i);       
        }

        /*
         * Calcul de la moyenne d'un symbole afin de retrouver le niveau de
         * chaque échantillon
         */
        for (int i = 0; i < nbEchTotal / nbEch; i++) {
            float moy_symbole = total[i] / (float) nbEch;
            //System.out.println("Moy symbole : "+moy_symbole);

            switch (form) {
                case "RZ":
                    informationAEmettre.add((Math.abs(amplMax * dutyCycleRZ - moy_symbole) < Math.abs(amplMin * dutyCycleRZ - moy_symbole)));
                    break;
                case "NRZ":
                    informationAEmettre.add((Math.abs(amplMax - moy_symbole) < Math.abs(amplMin - moy_symbole)));
                    break;
                case "NRZT":
                    informationAEmettre.add((Math.abs(amplMax * (1 - tmpMontee / 2) - moy_symbole) < Math.abs(amplMin * (1 - tmpMontee / 2) - moy_symbole)));
                    break;
            }
                //TODO check if we replace inforamtion à emetrre par un Float[]  for performance ?
        }

        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationAEmettre);
        }

        this.informationEmise = informationAEmettre;
    }

}
