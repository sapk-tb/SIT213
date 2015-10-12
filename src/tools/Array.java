package tools;

import information.Information;

/**
 *
 * @author Antoine GIRARD
 */

//TODO optimize
public class Array {

    /**
     * Additionne deux table de Float
     *
     * @param t1 la première table de Float
     * @param t2 la deuxième table de Float
     * @return la somme des tableaux
     */
    public static Float[] SumArrays(Float[] t1, Float[] t2) {
        int size = Math.max(t1.length, t2.length);
        Float[] tab = new Float[size];
        for (int i = 0; i < tab.length; i++) {
            tab[i] = t1[i] + t2[i];
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
    public static float[] SumArrays(float[] t1, float[] t2) {
        int size = Math.max(t1.length, t2.length);
        float[] tab = new float[size];
        for (int i = 0; i < tab.length; i++) {
            tab[i] = t1[i] + t2[i];
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
    public static Information SumArrays(Information i1, Information i2) {
        Float[] t1 = new Float[i1.nbElements()];
        Float[] t2 = new Float[i2.nbElements()];

        i1.toArray(t1);
        i2.toArray(t2);

        Float[] array = SumArrays(t1, t2);
        return new Information(array);
    }
}
