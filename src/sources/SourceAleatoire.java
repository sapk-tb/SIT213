package sources;

import information.Information;
import java.util.Random;

/**
 * Classe d'un composant source d'informations aleatoire dont les elements sont de type Boolean
 * @author Antoine GIRARD
 * @author Cedric HERZOG
 */
public class SourceAleatoire extends Source<Boolean> {

    /**
    * Un constructeur qui genere nbBits aleatoirement
     * @param nbBits nombre de bit a generer
    */
    public SourceAleatoire(int nbBits) {
        this(nbBits, (int) (Math.random() * 1024));
    }

    /**
    * Un constructeur qui genere nbBits aleatoirement base sur un seed
     * @param nbBits nombre de bit a generer
     * @param seed graine utilise comme base pour le message pseudo-aleatoire
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
