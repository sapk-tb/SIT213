package visualisations;

import information.Information;

/**
 * Classe réalisant l'affichage de répartition de valeurs composée d'élèments
 * réels (float)
 *
 * @author Antoine GIRARD
 */
public class SondeRepartitionAnalogique extends Sonde<Float> {

    //TODO use a cubic représentation and add some textual inf to the vue
    private float resolution = 0.01f;
    private final float max;
    private final float min;
    private int nbPixels = 10; //Largeur en pixel par point 

    /**
     * pour construire une sonde répartition analogique
     *
     * @param nom le nom de la fenêtre d'affichage
     * @param min la limite à gauche du graphique
     * @param max la limite à gauche du graphique
     * @throws java.lang.Exception
     */
    public SondeRepartitionAnalogique(String nom, float min, float max) throws Exception {
        super(nom);
        if (min > max) {
            throw new Exception("Error : min > max");
        }
        this.min = min;
        this.max = max;
    }

    /**
     * pour construire une sonde répartition analogique avec une résolution d'analyse définie
     *
     * @param nom le nom de la fenêtre d'affichage
     * @param min la limite à gauche du graphique
     * @param max la limite à gauche du graphique
     * @param resolution la résolution de l'analyse
     * @throws java.lang.Exception
     */
    public SondeRepartitionAnalogique(String nom, float min, float max, float resolution) throws Exception {
        this(nom, min, max);
        this.resolution = resolution;
    }

    /**
     * pour construire une sonde répartition analogique avec une résolution d'analyse définie avec un nombre de pixel par pas
     *
     * @param nom le nom de la fenêtre d'affichage
     * @param min la limite à gauche du graphique
     * @param max la limite à gauche du graphique
     * @param resolution la résolution de l'analyse
     * @param nbPixels Nombre de pixel par pas
     * @throws java.lang.Exception
     */
    public SondeRepartitionAnalogique(String nom, float min, float max, float resolution, int nbPixels) throws Exception {
        this(nom, min, max, resolution);
        this.nbPixels = nbPixels;
    }

    public void recevoir(Information<Float> information) {
        informationRecue = information;
        //this.resolution = 10000/information.nbElements();
        int nbElements = (int) ((max - min) / resolution);
        System.out.println("max : " + max + " min : " + min + " nbElements : " + nbElements);
        //float[] table = new float[nbElements*2]; //on multiplie par 2 afin de faire une signal carré
        float[] table = new float[nbElements];
        for (float f : information) {
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
