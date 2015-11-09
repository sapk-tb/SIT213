package sources;

import information.Information;
import java.util.Random;

/**
 * Classe d'un composant source d'informations aléatoires dont les éléments sont de type Boolean
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 */
public class SourceAleatoire extends Source<Boolean> {

    /**
    * Un constructeur qui génère nbBits aléatoirement
     * @param nbBits nombre de bit à générer
    */
    public SourceAleatoire(int nbBits) {
        this(nbBits, (int) (Math.random() * 1024));
    }

    /**
    * Un constructeur qui génère nbBits aléatoirement basé sur un seed
     * @param nbBits nombre de bits à générer
     * @param seed graine utilisé comme base pour le message pseudo-aléatoire
    */
    public SourceAleatoire(int nbBits, int seed) {
        super();
        this.informationGeneree = new Information<>(nbBits);
        Random generator = new Random(seed);
        for (int i = 0; i < nbBits; i++) {
            this.informationGeneree.add(generator.nextBoolean());
        }
    }
}
