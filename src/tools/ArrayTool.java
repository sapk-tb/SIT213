package tools;

import information.Information;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import sources.SourceBruitGaussien;
import tools.Thread.AddToFloatArray;
import tools.Thread.AddToNativeFloatArray;

/**
 *
 * @author Antoine GIRARD
 */
//TODO optimize
public class ArrayTool {

    /**
     * Additionne deux table de Float
     *
     * @param t1 la première table de Float
     * @param t2 la deuxième table de Float
     * @return la somme des tableaux
     */
    public static Float[] sumArrays(Float[] t1, Float[] t2) {
        int size = Math.max(t1.length, t2.length);
        Float[] tab = new Float[size];

        int nb_thread = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(nb_thread);
        int nbEchByThread = Math.max(size / nb_thread, 1);
        int i = 0;
        while (i < size) {
            int end = Math.min(i + nbEchByThread, size);
            pool.execute(new AddToFloatArray(tab, t1, t2, i, end));
            i = end;
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
     * Additionne deux table de float
     *
     * @param t1 la première table de float
     * @param t2 la deuxième table de float
     * @return la somme des tableaux
     */
    public static float[] sumArrays(float[] t1, float[] t2) {
        int size = Math.max(t1.length, t2.length);
        float[] tab = new float[size];
        int nb_thread = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(nb_thread);
        int nbEchByThread = Math.max(size / nb_thread, 1);
        int i = 0;
        while (i < size) {
            int end = Math.min(i + nbEchByThread, size);
            pool.execute(new AddToNativeFloatArray(tab, t1, t2, i, end));
            i = end;
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
     * @return la somem des deux informations
     */
    public static Information sumArrays(Information i1, Information i2) {
        Float[] t1 = new Float[i1.nbElements()];
        Float[] t2 = new Float[i2.nbElements()];

        i1.toArray(t1);
        i2.toArray(t2);

        Float[] array = sumArrays(t1, t2);
        return new Information(array);
    }
}