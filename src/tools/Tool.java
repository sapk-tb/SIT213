/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import information.Information;

/**
 * Classe d'outils static
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 * @author Pierrick CHOVELON
 * @author Mélanie CORRE
 */
public class Tool {
    /*
     public static float getMoyenne(Information<Float> inf) {
     float total = 0;
     for (Float content : inf) {
     total += (float) content;
     }
     return total / inf.nbElements();
     }
     */

    /**
     * Retourne le pourcentage de différence entre deux tableau de Boolean
     *
     * @param a Le premier tableau
     * @param b le second tableau
     * @return float : le rapport erreur/nbSymbole
     */
    public static float compare(Boolean[] a, Boolean[] b) {
        int nbSymbole = a.length;
        int errors = 0;
        for (int i = 0; i < nbSymbole; i++) {
            if (a[i] != b[i]) {
                errors++;
            }
        }
        return (float) errors / (float) nbSymbole;
    }

    /**
     * Retourne la puissance d'une information contenant des échantillons
     *
     * @param inf l'Information contenant le signal
     * @return
     */
    //*
    public static float getPuissance(Information<Float> inf) {
        if (inf == null) {
            throw new NullPointerException("Information non définie");
        }
        Double total = 0.0;
        for (Float content : inf) {
            total += Math.pow(content, 2);
        }
        return (float) (total / (float) inf.nbElements());
    }

    //*/
    /*
     public static float getPuissance(Information<Float> inf) {

     Float total = inf.getStream().reduce(0f, Float::sum);
     //        double total = inf.getStream().mapToDouble(d->d).sum();
     //.mapToDouble(Double::doubleValue).sum();

     return (float) (total / (float) inf.nbElements());
     }
     //*/
    /**
     * Fait la transformation de dB en linaire
     *
     * @param db la valeur à linéariser
     * @return
     */
    public static float dBToLin(float db) {
        return (float) Math.pow(10, db / 10f);
    }
}
