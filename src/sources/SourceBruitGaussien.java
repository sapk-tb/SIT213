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
     * @param nbEch nombre de bit à générer
    */
    public SourceBruitGaussien(int nbEch) {
        this(nbEch, 1f);
    }    
    /**
    * Un constructeur qui génère nbEch aléatoirement
     * @param nbEch Nombre de bit à générer
     * @param puissance Puissance du signal
    */
    public SourceBruitGaussien(int nbEch, float puissance) {
        super();
        this.informationGeneree = new Information<Float>();
        Random generator = new Random((long) (Math.random() * 1024));
        for (int i = 0; i < nbEch; i++) {
            this.informationGeneree.add((float)Math.sqrt(puissance)*((float) generator.nextGaussian())); //TODO check 
        }
    }
}
