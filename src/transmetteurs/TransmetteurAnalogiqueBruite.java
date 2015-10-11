package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import sources.SourceBruitGaussien;
import tools.Tool;

/**
 * Classe d'un composant qui transmet des informations de type Float sans
 * défaut.
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 */
public class TransmetteurAnalogiqueBruite extends Transmetteur<Float, Float> {

    /**
     * l'information reçue en entrée du transmetteur
     */
    protected Information<Float> informationBruit;
    private final float SNR;

    public TransmetteurAnalogiqueBruite(float SNR) {
        super();
        this.SNR = SNR;
    }

    /**
     * reçoit une information. Cette méthode, en fin d'exécution, appelle la
     * méthode emettre.
     *
     * @param information l'information reçue
     * @throws information.InformationNonConforme Quand l'information est
     * invalide
     */
    @Override
    public void recevoir(Information<Float> information) throws InformationNonConforme {
        if (information == null) {
            throw new InformationNonConforme("information recue == null");
        }
        this.informationRecue = information;
        emettre();
    }

    /**
     * émet l'information construite par la transmetteur
     */
    @Override
    public void emettre() throws InformationNonConforme {

        //TODO add bruit
        //TODO implement snr to constructor
        float puissance_signal = Tool.getPuissance(this.informationRecue);
        float puissance_bruit = 0;

        if (this.SNR != 0f) {
            puissance_bruit = puissance_signal / this.SNR;
        }
        //Float[] output = new Float[this.informationRecue.nbElements()];
        //*
        int nbEl = this.informationRecue.nbElements();
        this.informationEmise = new Information<Float>(nbEl);
        //System.out.println("nbEl : " + nbEl);
        Float[] output = new Float[nbEl];
        this.informationRecue.toArray(output);
        //*/
        /* Génération du Bruit */
        SourceBruitGaussien bruit = new SourceBruitGaussien(nbEl, puissance_bruit);
        bruit.emettre();
        this.informationBruit = bruit.getInformationEmise();
        Float[] b = new Float[nbEl];
        this.informationBruit.toArray(b);

        System.out.println("Puissance signal recu : " + puissance_signal + " / SNR canal " + this.SNR + " / Puissance du bruit à appliquer " + puissance_bruit + " / Puissance réel du bruit " + Tool.getPuissance(this.informationBruit));
        //for (int i = 0; i < this.informationRecue.nbElements(); i++) {
        /*
         for (int i = 0; i < nbEl; i++) {
         this.informationEmise.add(this.informationRecue.iemeElement(i) + this.informationBruit.iemeElement(i));
         //output[i] += b[i];
         //output[i] = (this.informationRecue.iemeElement(i) + this.informationBruit.iemeElement(i));
         }
         */

        class AddBruitThread implements Runnable {

            private final Float[] signal;
            private final Float[] bruit;
            private final int index_start;
            private final int index_end;

            AddBruitThread(Float[] s, Float[] b, int start, int end) {
                bruit = b;
                signal = s;
                index_start = start;
                index_end = end;
            }

            @Override
            public void run() {
                for (int i = index_start; i < index_end; i++) {
                    signal[i] += bruit[i];
                }
            }
        }

        int nb_thread = Runtime.getRuntime().availableProcessors();

        ExecutorService pool = Executors.newFixedThreadPool(nb_thread);
        int nbEchByThread = Math.max(nbEl / nb_thread, 1);
        int i = 0;
        while (i < nbEl) {
            //int start = i;
            int end = Math.min(i + nbEchByThread, nbEl);
            pool.execute(new AddBruitThread(output, b, i, end));
            i = end;
        }
        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(SourceBruitGaussien.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.informationEmise = new Information<Float>(output);
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }

}
