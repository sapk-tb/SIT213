/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recepteurs;

import information.Information;
import information.InformationNonConforme;
import tools.Statistic;

/**
 * Classe d'un composant recepteur d'informations dont les élèments sont de type
 * Double qui hérite de la classe Recepteur
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 * @author Pierrick CHOVELON
 * @author Mélanie CORRE
 */
public class RecepteurAnalogiqueMultiIntelligent extends RecepteurAnalogiqueMulti {

    public RecepteurAnalogiqueMultiIntelligent(String form, int nbEch, double amplMin, double amplMax, double dutyCycleRZ, double tmpMontee, Integer[] dt, Double[] ar, boolean noMultiCorrection) {
        super(form, nbEch, amplMin, amplMax, dutyCycleRZ, tmpMontee, dt, ar, noMultiCorrection);
    }

    public RecepteurAnalogiqueMultiIntelligent(String form, Integer nbEch, double dutyCycleRZ, double tmpMontee) {
        this(form, nbEch, 0, 0, dutyCycleRZ, tmpMontee, new Integer[5], new Double[5], false); // Par defaut on essaie de corriger les trajet multiple même si on le sconait pas encore

        for (int i = 0; i < dt.length; i++) { // Inti des multi trajet abat analyse
            dt[i] = 0;
            ar[i] = 0.0;
        }
    }

    /**
     * reçoit une information. Cette méthode, en fin d'exécution, appelle la
     * méthode emettre.
     *
     * @param information l'information reçue
     * @throws information.InformationNonConforme
     */
    @Override
    public void recevoir(Information<Double> information) throws InformationNonConforme {
        this.informationRecue = information;
        Double[] data = new Double[this.informationRecue.nbElements()];
        this.informationRecue.toArray(data);
        Statistic stats = new Statistic(data);
        this.amplMax = stats.getMax();
        this.amplMin = stats.getMin();
        System.out.println("Valeurs estimée : [Etat haut : "+ this.amplMax+", Etat bas : "+ this.amplMin+"]");
        //TODO detectect and clean noise and multi 
        emettre();
    }
}
