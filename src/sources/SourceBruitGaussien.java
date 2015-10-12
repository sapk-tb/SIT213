package sources;

import information.Information;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 */
public class SourceBruitGaussien extends Source<Float> {

    private final int seed;

    /**
     * Un constructeur qui génère nbEch aléatoirement
     *
     * @param nbEch nombre de bit à générer
     */
    public SourceBruitGaussien(int nbEch) {
        this(nbEch, 1f, (int) (Math.random() * 1024));
    }

    public SourceBruitGaussien(int nbEch, float puissance) {
        this(nbEch, puissance, (int) (Math.random() * 1024));
    }

    /**
     * Un constructeur qui génère nbEch aléatoirement
     *
     * @param nbEch Nombre de bit à générer
     * @param puissance Puissance du signal
     */
    /*
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
    //*
    public SourceBruitGaussien(int nbEch, float puissance, final int seed) {
        super();
        this.seed = seed;
        //this.informationGeneree = new Information<Float>(nbEch);
        Float[] bruit = new Float[nbEch];
        float puissance_sqrt = (float) Math.sqrt(puissance);
        int nb_thread = Runtime.getRuntime().availableProcessors();

        class GetNextGaussionThread implements Runnable {

            private final float puissance_sqrt;
            private final Float[] bruit;
            private final int index_start;
            private final int index_end;
            private final Random rand = new Random(seed);

            GetNextGaussionThread(Float[] b, int start, int end, float p) {
                bruit = b;
                puissance_sqrt = p;
                index_start = start;
                index_end = end;
            }

            @Override
            public void run() {
                for (int i = index_start; i < index_end; i++) {
                    bruit[i] = (puissance_sqrt * ((float) rand.nextGaussian()));
                }
            }
        }

        ExecutorService pool = Executors.newFixedThreadPool(nb_thread);
        int nbEchByThread = Math.max(nbEch / nb_thread, 1);
        int i = 0;
        while (i < nbEch) {
            //int start = i;
            int end = Math.min(i + nbEchByThread, nbEch);
            pool.execute(new GetNextGaussionThread(bruit, i, end, puissance_sqrt));
            i = end;
        }

        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(SourceBruitGaussien.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.informationGeneree = new Information<Float>(bruit);

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
