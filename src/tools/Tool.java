package tools;

import information.Information;
import java.util.Objects;

/**
 * Classe d'outils static
 *
 * @author Antoine GIRARD
 * @author Cedric HERZOG
 * @author Pierrick CHOVELON
 * @author Melanie CORRE
 */
public class Tool {
    /*
     public static double getMoyenne(Information<Float> inf) {
     double total = 0;
     for (Float content : inf) {
     total += (double) content;
     }
     return total / inf.nbElements();
     }
     */

    /**
     * Retourne le pourcentage de difference entre deux tableau de Boolean
     *
     * @param a Le premier tableau
     * @param b le second tableau
     * @return double : le rapport erreur/nbSymbole
     */
    public static double compare(Boolean[] a, Boolean[] b) {
        int nbSymbole = Math.min(a.length,b.length);
        int errors = 0;
        for (int i = 0; i < nbSymbole; i++) {
            if (!Objects.equals(a[i], b[i])) {
                errors++;
            }
        }
        return (double) errors / (double) nbSymbole;
    }

    /**
     * Retourne la puissance d'une information contenant des echantillons
     *
     * @param inf l'Information contenant le signal
     * @return Retourne la puissance du signal
     */
    //*
    public static double getPuissance(Information<Double> inf) {
        if (inf == null) {
            throw new NullPointerException("Information non definie");
        }
        Double total = 0.0;
        for (Double content : inf) {
            total += Math.pow(content, 2);
        }
        return (total / (double) inf.nbElements());
    }

    //*/
    /*
     public static double getPuissance(Information<Float> inf) {

     Float total = inf.getStream().reduce(0f, Float::sum);
     //        double total = inf.getStream().mapToDouble(d->d).sum();
     //.mapToDouble(Double::doubleValue).sum();

     return (double) (total / (double) inf.nbElements());
     }
     //*/
    /**
     * Fait la transformation de dB en linaire
     *
     * @param db la valeur a lineariser
     * @return Renvoie la valeur en lineaire
     */
    public static double dBToLin(double db) {
        return Math.pow(10, db / 10f);
    }

}
