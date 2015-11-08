package tools;

import information.Information;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import sources.SourceBruitGaussien;
import tools.Thread.AddToDoubleArray;
import tools.Thread.AddToNativeDoubleArray;

/**
 *
 * @author Antoine GIRARD
 * @author Cedric HERZOG
 */
public class ArrayTool {

    /**
     * Additionne deux tables de Double
     *
     * @param t1 la première table de Double
     * @param t2 la deuxième table de Double
     * @return la somme des tableaux
     */
    public static Double[] sumArrays(Double[] t1, Double[] t2) {
        int size = Math.max(t1.length, t2.length);
        Double[] tab = new Double[size];

        int nb_thread = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(nb_thread);
        int nbEchByThread = 1 + (size / nb_thread);
        int i = 0;
        while (i < size) {
            try {
                int end = Math.min(i + nbEchByThread, size - 1);
                pool.execute(new AddToDoubleArray(tab, t1, t2, i, end));
                i = end + 1;
            } catch (Exception ex) {
                Logger.getLogger(ArrayTool.class.getName()).log(Level.SEVERE, "Une erreur dans le calcul de l'index final est apparue", ex);
            }
        }
        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(SourceBruitGaussien.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tab;
    }

    /**
     * Additionne deux tables de double
     *
     * @param t1 la première table de double
     * @param t2 la deuxième table de double
     * @return la somme des tableaux
     */
    public static double[] sumArrays(double[] t1, double[] t2) {
        int size = Math.max(t1.length, t2.length);
        double[] tab = new double[size];
        int nb_thread = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(nb_thread);
        int nbEchByThread = Math.max(size / nb_thread, 1);
        int i = 0;
        while (i < size) {
            try {
                int end = Math.min(i + nbEchByThread, size - 1);
                pool.execute(new AddToNativeDoubleArray(tab, t1, t2, i, end));
                i = end + 1;
            } catch (Exception ex) {
                Logger.getLogger(ArrayTool.class.getName()).log(Level.SEVERE, "Une erreur dans le calcul de l'index final est apparue", ex);
            }
        }
        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(SourceBruitGaussien.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tab;
    }

    /**
     * Additionne deux Informations
     *
     * @param i1 la première Information
     * @param i2 la deuxième Information
     * @return la somme des deux informations
     */
    public static Information sumArrays(Information i1, Information i2) {
        Double[] t1 = new Double[i1.nbElements()];
        Double[] t2 = new Double[i2.nbElements()];

        i1.toArray(t1);
        i2.toArray(t2);

        Double[] array = sumArrays(t1, t2);
        return new Information(array);
    }

    /**
     * Facteur table de double
     *
     * @param i1 Information à factoriser
     * @param ar Facteur de factorisation
     * @return le tableau multiplié par le facteur (Dans une information)
     */
    public static Information factArrays(Information<Double> i1, double ar) {
        //Création d'un tableau contenant l'information factorisée
        int size = i1.nbElements();
        Double[] tab = new Double[size];
        for (int i = 0; i < size; i++) {
            tab[i] = i1.iemeElement(i) * ar;
        }
        //Créé et retourne une nouvelle info contenant le tableau précédent
        return new Information(tab);
    }
}
