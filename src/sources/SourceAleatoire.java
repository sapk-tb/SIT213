package sources;

import information.Information;
import java.util.Random;

public class SourceAleatoire extends Source<Boolean> {

    public SourceAleatoire(int nbBits) {
        super();
        this.informationGeneree = new Information<Boolean>();
        for (int i = 0; i < nbBits; i++) {
            this.informationGeneree.add(Math.random() < 0.5);
        }
    }
    public SourceAleatoire(int nbBits,int seed) {
        super();
        this.informationGeneree = new Information<Boolean>();
        Random generator = new Random(seed);
        for (int i = 0; i < nbBits; i++) {
            this.informationGeneree.add(generator.nextDouble() < 0.5);
        }
    }
}
