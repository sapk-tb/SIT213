package sources;

import information.Information;
import java.util.Random;

/**
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 */
public class SourceBruitGaussien extends Source<Float> {

    /**
     * Un constructeur qui génère nbEch aléatoirement
     *
     * @param nbEch nombre de bit à générer
     */
    public SourceBruitGaussien(int nbEch) {
        this(nbEch, 1f);
    }

    /**
     * Un constructeur qui génère nbEch aléatoirement
     *
     * @param nbEch Nombre de bit à générer
     * @param puissance Puissance du signal
     */
    //*
    public SourceBruitGaussien(int nbEch, float puissance) {
        super();
        this.informationGeneree = new Information<Float>(nbEch);
        Random generator = new Random();
        float puissance_sqrt = (float) Math.sqrt(puissance);
        for (int i = 0; i < nbEch; i++) {
            this.informationGeneree.add(puissance_sqrt * ((float) generator.nextGaussian()));
        }
    }
     //*/
    /*
     public SourceBruitGaussien(int nbEch, float puissance) {
     super();
     Random generator = new Random();
     Float[] data = new Float[nbEch];
     float puissance_sqrt = (float) Math.sqrt(puissance);
     for (int i = 0; i < nbEch; i++) {
     data[i] = (puissance_sqrt * ((float) generator.nextGaussian()));
     }
     this.informationGeneree = new Information<Float>(data);
     }
     //*/

}
