package visualisations;

import information.Information;

/**
 * Classe réalisant l'affichage de répartition de valeurs composé d'éléments
 * réels (double)
 *
 * @author Antoine GIRARD
 */
public class SondeRepartitionAnalogique extends Sonde<Double> {

    private final double max;
    private final double min;
    private int nbPixels = 10; //Largeur en pixel par point 
    private int nbInterval = 1000;

    /**
     * pour construire une sonde répartition analogique
     *
     * @param nom le nom de la fenêtre d'affichage
     * @param min la limite à gauche du graphique
     * @param max la limite à gauche du graphique
     * @throws java.lang.Exception Si le max est inférieur à min
     */
    public SondeRepartitionAnalogique(String nom, double min, double max) throws Exception {
        super(nom);
        if (min > max) {
            throw new Exception("Error : min > max");
        }
        this.min = min;
        this.max = max;
    }

    /**
     * pour construire une sonde répartition analogique avec une résolution
     * d'analyse définie
     *
     * @param nom le nom de la fenêtre d'affichage
     * @param min la limite à gauche du graphique
     * @param max la limite à gauche du graphique
     * @param nbInterval le nombre d'intervalles de l'analyse
     * @throws java.lang.Exception Si le max est inférieur à min
     */
    public SondeRepartitionAnalogique(String nom, double min, double max, int nbInterval) throws Exception {
        this(nom, min, max);
        this.nbInterval = nbInterval;
    }

    /**
     * pour construire une sonde répartition analogique avec une résolution
     * d'analyse définie avec un nombre de pixel par pas
     *
     * @param nom le nom de la fenêtre d'affichage
     * @param min la limite à gauche du graphique
     * @param max la limite à gauche du graphique
     * @param nbInterval le nombre d'intervalle de l'analyse
     * @param nbPixels Nombre de pixel par pas
     * @throws java.lang.Exception Si le max est inférieur à min
     */
    public SondeRepartitionAnalogique(String nom, double min, double max, int nbInterval, int nbPixels) throws Exception {
        this(nom, min, max, nbInterval);
        this.nbPixels = nbPixels;
    }

    public void recevoir(Information<Double> information) {
        informationRecue = information;
        //this.resolution = 10000/information.nbElements();
        int nbElements = nbInterval;
        double resolution = ((max - min) / nbInterval);
        System.out.println("max : " + max + " min : " + min + " nbElements : " + nbElements);
        //double[] table = new double[nbElements*2]; //on multiplie par 2 afin de faire une signal carré
        double[] table = new double[nbElements];
        for (double f : information) {
            //System.out.println("Test : " + f + /*" / " + f % resolution + */" / "+(f-(f%resolution))+" / "+(int)(f-(f%resolution) - min)*1/resolution + " / " +(int)(f*resolution) - min );
            //table[(int) ((f - (f % resolution) - min) * 1 / resolution)]++;
            int index = (int) ((f - min) * 1 / resolution);
            if (index >= 0 && index < nbElements) {
                /*
                 int index = (int)((f-min)*1/resolution)*2;
                 table[index]++;
                 table[index+1]++;
                 */
                table[index]++;
            } else {
                //System.out.println("Value hors intervalle : " + f + " Index : " + (int) ((f - min) * 1 / resolution));
            }
        }
        new VueCourbe(table, nbPixels, nom);
    }

}
