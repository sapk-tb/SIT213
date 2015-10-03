/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private final float dutyCycleRZ;
    private final float tmpMontee;

    /**
     * Constructeur du récepteur analogique
     *
     * @param form
     * @param nbEch
     * @param amplMin
     * @param amplMax
     * @param dutyCycleRZ
     * @param tmpMontee
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
        Information<Boolean> informationAEmettre = new Information<Boolean>();
        float total[] = new float[informationRecue.nbElements() / nbEch];
        /*
         * Calcul de la somme pour chaque échantillon
         */
        for (int i = 0; i < informationRecue.nbElements(); i++) {
            total[(int) i / nbEch] += informationRecue.iemeElement(i);
        }

        /*
         * Calcul de la moyenne d'un symbole afin de retrouver le niveau de
         * chaque échantillon
         */
        for (int i = 0; i < informationRecue.nbElements() / nbEch; i++) {
            float moy_symbole = total[i] / (float) nbEch;
            /* if (Math.abs(amplMax-moy_symbole) < Math.abs(amplMin-moy_symbole)) {
             informationAEmettre.add(true);
             } else {
             informationAEmettre.add(false);
             }*/
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

        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationAEmettre);
        }

        this.informationEmise = informationAEmettre;
    }

}
