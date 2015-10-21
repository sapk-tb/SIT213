package emetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

/**
 * Classe d'un composant emetteur d'informations dont les élèments sont de type
 * EmetteurAnalogique qui hérite de la classe Emetteur
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 * @author Pierrick CHOVELON
 * @author Mélanie CORRE
 */
public class EmetteurAnalogique extends Emetteur<Boolean, Double> {

    private final String form;
    private final int nbEch;

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

    public double getTmpMontee() {
        return tmpMontee;
    }

    public double getDutyCycleRZ() {
        return dutyCycleRZ;
    }
    private final double amplMin;
    private final double amplMax;
    private final double tmpMontee;
    private final double dutyCycleRZ;

    /**
     * un constructeur de l'émetteur analogique
     *
     * @param form Forme du signal à émettre
     * @param nbEch Nombre d'écahntillon par symbole
     * @param amplMin Amplitude pour la valeur binaire 0
     * @param amplMax Amplitude pour la valeur binaire 1
     * @param dutyCycleRZ Dutycycle à utiliser dans le cadre d'une forme RZ
     * @param tmpMontee Temps de montée à respecté dans le cadre d'une forme
     * NRZT
     */
    public EmetteurAnalogique(String form, int nbEch, double amplMin, double amplMax, double dutyCycleRZ, double tmpMontee) {
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
    public void recevoir(Information<Boolean> information) throws InformationNonConforme {
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

        double niveauPrecedent = 0f;
        int nbEchTransition = (int) (tmpMontee * nbEch); //TODO determine if not should be Math.ceil or keep a truncature ?
        double deltaAmplitude = amplMax - amplMin;
        double coefDirecteur = deltaAmplitude / nbEchTransition;
        double deltaEntreEch = coefDirecteur * 1; // *1 : echantillon

        int nbSymbole = informationRecue.nbElements();
        Information<Double> informationAEmettre = new Information<Double>(nbSymbole * nbEch);

        double tempsA0 = nbEch * (1 - dutyCycleRZ) / 2;
        //System.out.println("Tier : " + tempsA0+" dutyCycleRZ : " + dutyCycleRZ+" nbEch : " + nbEch);

        //System.out.println("Debug : " + nbEchTransition + " / " + deltaAmplitude + " / " + coefDirecteur + " / " + deltaEntreEch);
        for (int i = 0; i < nbSymbole; i++) {
            Boolean bit = informationRecue.iemeElement(i);
            for (int n = 0; n < nbEch; n++) {
                /*
                 * Pour chaque échantillon de chaque information reçue, on
                 * vérifie : si la forme du signal est RZ et si l'échantillon
                 * courant est inférieur au nombre d'échantillon * le dutyCycle,
                 * on ajoute l'amplitude maximale si le bit vaut vrai,
                 * l'amplitude minimale si le bit vaut faux, autrement on ajoute
                 * 0.
                 */
                switch (form) {
                    case "RZ":
                        if (n < tempsA0 || n > nbEch-tempsA0) {
                            informationAEmettre.add(0.0);
                        } else {
                            informationAEmettre.add(bit ? amplMax : amplMin);
                        }
                        break;
                    case "NRZ":
                        informationAEmettre.add(bit ? amplMax : amplMin);
                        break;
                    case "NRZT":
                        double niveauAnalogique = (bit ? amplMax : amplMin);
                        //TODO simplify
                        //System.out.println("Debug loop : " + niveauPrecedent + " / " + niveauAnalogique + " / " + deltaEntreEch);
                        if (i == 0 && n == 0) {
                            niveauPrecedent = 0; // On démarre à 0
                        } else {
                            if (Math.abs(niveauPrecedent - niveauAnalogique) > deltaEntreEch) {
                                if (Math.max(niveauPrecedent, niveauAnalogique) == niveauAnalogique) {
                                    niveauPrecedent = niveauPrecedent + deltaEntreEch;
                                } else {
                                    niveauPrecedent = niveauPrecedent - deltaEntreEch;
                                }
                            } else {
                                niveauPrecedent = niveauAnalogique;
                            }
                        }
                        informationAEmettre.add(niveauPrecedent);
                        break;
                }
            }
        }

        for (DestinationInterface<Double> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationAEmettre);
        }

        this.informationEmise = informationAEmettre;
    }

}
