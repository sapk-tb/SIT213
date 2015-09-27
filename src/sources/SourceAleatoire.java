package sources;

import information.Information;
import java.util.Random;

/**
 * Classe d'un composant source d'informations aléatoire dont les élèments sont de type Boolean
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 */
public class SourceAleatoire extends Source<Boolean> {

    /**
    * Un constructeur qui génère nbBits aléatoirement
     * @param nbBits
    */
    public SourceAleatoire(int nbBits) {
        this(nbBits, (int) (Math.random() * 1024));
    }

    /**
    * Un constructeur qui génère nbBits aléatoirement basé sur un seed
     * @param nbBits
     * @param seed
    */
    public SourceAleatoire(int nbBits, int seed) {
        super();
        this.informationGeneree = new Information<Boolean>();
        Random generator = new Random(seed);
        for (int i = 0; i < nbBits; i++) {
            this.informationGeneree.add(generator.nextBoolean());
        }
    }
}
