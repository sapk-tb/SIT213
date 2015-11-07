package recepteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

/**
 * Classe d'un composant recepteur d'informations dont les �l�ments sont de type
 * RecepteurAnalogique qui h�rite de la classe Recepteur
 *
 * @author Antoine GIRARD
 * @author C�dric HERZOG
 * @author Pierrick CHOVELON
 * @author M�lanie CORRE
 */
public class RecepteurAnalogique extends Recepteur<Double, Boolean> {

    protected final String form;
    protected final int nbEch;
    protected double amplMin;
    protected double amplMax;
    protected final double dutyCycleRZ;
    protected final double tmpMontee;

    public String getForm() {
        return form;
    }

    public int getNbEch() {
        return nbEch;
    }

    public double getAmplMin() {
        return amplMin;
    }

    public double getAmplMax() {
        return amplMax;
    }

    public double getDutyCycleRZ() {
        return dutyCycleRZ;
    }

    public double getTmpMontee() {
        return tmpMontee;
    }

    /**
     * Constructeur du r�cepteur analogique
     *
     * @param form Forme du signal � recevoir
     * @param nbEch Nombre d'�chantillon par symbole
     * @param amplMin Amplitude pour la valeur binaire 0
     * @param amplMax Amplitude pour la valeur binaire 1
     * @param dutyCycleRZ Dutycycle � utiliser dans le cadre d'une forme RZ
     * @param tmpMontee Temps de mont�e � respecter dans le cadre d'une forme
     * NRZT
     */
    public RecepteurAnalogique(String form, int nbEch, double amplMin, double amplMax, double dutyCycleRZ, double tmpMontee) {
        super();
        this.form = form;
        this.nbEch = nbEch;
        this.amplMin = amplMin;
        this.amplMax = amplMax;
        this.dutyCycleRZ = dutyCycleRZ;
        this.tmpMontee = tmpMontee;
    }

    /**
     * re�oit une information. Cette m�thode, en fin d'ex�cution, appelle la
     * m�thode emettre.
     *
     * @param information l'information re�ue
     */
    @Override
    public void recevoir(Information<Double> information) throws InformationNonConforme {
        this.informationRecue = information;
        emettre();
    }

    /**
     * Analyse une information contenant des �chantillons et en d�termine les
     * valeurs binaires en se basant sur les param�tres du recepteur
     *
     * @param infRecue l'information contenant les d'�chantillons
     * @return les informations binaires d�duites
     * @throws InformationNonConforme
     */
    protected Information<Boolean> parseEch(Information<Double> infRecue) throws InformationNonConforme {
        if (infRecue == null) {
            throw new InformationNonConforme("informationRecue == null");
        }

        int nbEchTotal = infRecue.nbElements();
        int nbSymbole = nbEchTotal / nbEch;
        Information<Boolean> informationAEmettre = new Information<Boolean>(nbSymbole);
        double total[] = new double[nbSymbole];

        /*
         * Calcul de la somme pour chaque �chantillon
         */
        for (int i = 0; i < nbSymbole; i++) {
            for (int n = 0; n < nbEch; n++) {
                total[i] += infRecue.iemeElement(i * nbEch + n);
            }
        }

        /*
         * Calcul de la moyenne d'un symbole afin de retrouver le niveau de
         * chaque �chantillon
         */
        for (int i = 0; i < nbEchTotal / nbEch; i++) {
            double moy_symbole = total[i] / (double) nbEch;
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
        }

        return informationAEmettre;
    }

    /**
     * Envoie l'informationEmise aux �l�ments connect�s
     *
     * @throws InformationNonConforme
     */
    protected void envoyerAuxSuivants() throws InformationNonConforme {
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(this.informationEmise);
        }
    }

    /**
     * �met l'information construite par l'emetteur
     *
     * @throws information.InformationNonConforme
     */
    @Override
    public void emettre() throws InformationNonConforme {
        this.informationEmise = parseEch(this.informationRecue);
        envoyerAuxSuivants();
    }

}
