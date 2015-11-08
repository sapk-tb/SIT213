package sources;

import information.Information;
//import tools.Random;
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
public class SourceBruitGaussien extends Source<Double> {

    private final int seed;

    /**
     * Un constructeur qui génère nbEch aléatoirement
     *
     * @param nbEch nombre de bits à générer
     */
    public SourceBruitGaussien(int nbEch) {
        this(nbEch, 1f, (int) (Math.random() * 1024));
    }

    /**
     * Un constructeur qui génère nbEch aléatoirement
     *
     * @param nbEch nombre de bits à générer
     * @param puissance puissance du bruit
     */
    public SourceBruitGaussien(int nbEch, double puissance) {
        this(nbEch, puissance, (int) (Math.random() * 1024));
    }

    /**
     * Un constructeur qui génère nbEch aléatoirement rapidement
     *
     * @param nbEch Nombre de bits à générer
     * @param puissance Puissance du signal
     * @param seed le grain de génération
     * @param quick Utiliser un mode rapide mais moins proche de la réalité
     *
     */
    //*
    public SourceBruitGaussien(int nbEch, double puissance, final int seed, boolean quick) {
        this.seed = seed;
        if (quick) {
            int nbEchQuick = (int) Math.max(1000, nbEch / 1000);
            System.out.println("Source de bruit mode quick enabled : " + nbEchQuick);
            Double[] bruitQuick = createBruit(nbEchQuick, puissance, this.seed);
            this.informationGeneree = new Information<Double>(nbEch);
            Random rand = new Random(this.seed);
            for (int i = 0; i < nbEch; i++) {
                this.informationGeneree.add(bruitQuick[rand.nextInt(nbEchQuick)]);
            }
        } else {
            this.informationGeneree = new Information<Double>(createBruit(nbEch, puissance, this.seed));
        }
    }

    /**
     * Un constructeur qui génère nbEch aléatoirement
     *
     * @param nbEch Nombre de bits à générer
     * @param puissance Puissance du signal
     * @param seed le grain de génération
     *
     */
    //*
    public SourceBruitGaussien(int nbEch, double puissance, final int seed) {
        this(nbEch, puissance, seed, false);
    }

    private static Double[] createBruit(int nbEch, double puissance, final int seed) {
        Double[] bruit = new Double[nbEch];
        double puissance_sqrt = Math.sqrt(puissance);
        int nb_thread = Runtime.getRuntime().availableProcessors();

        class GetNextGaussionThread implements Runnable {

            private final double puissance_sqrt;
            private final Double[] bruit;
            private final int index_start;
            private final int index_end;
            private final Random rand = new Random(seed);

            GetNextGaussionThread(Double[] b, int start, int end, double p) {
                bruit = b;
                puissance_sqrt = p;
                index_start = start;
                index_end = end;
            }

            @Override
            public void run() {
                for (int i = index_start; i < index_end; i++) {
                    bruit[i] = (puissance_sqrt * rand.nextGaussian());
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
        return bruit;
    }
}
