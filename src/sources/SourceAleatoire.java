package sources;

import information.Information;
import java.util.Random;

/**
 * Classe d'un composant source d'informations al�atoire dont les �l�ments sont de type Boolean
 * @author Antoine GIRARD
 * @author C�dric HERZOG
 */
public class SourceAleatoire extends Source<Boolean> {

    /**
    * Un constructeur qui g�n�re nbBits al�atoirement
     * @param nbBits nombre de bit � g�n�rer
    */
    public SourceAleatoire(int nbBits) {
        this(nbBits, (int) (Math.random() * 1024));
    }

    /**
    * Un constructeur qui g�n�re nbBits al�atoirement bas� sur un seed
     * @param nbBits nombre de bit � g�n�rer
     * @param seed graine utilis� comme base pour le message pseudo-al�atoire
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
