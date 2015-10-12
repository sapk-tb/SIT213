package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;
import sources.SourceBruitGaussien;
import tools.Array;
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
    private Float SNR = null;
    private int seed;

    public TransmetteurAnalogiqueBruite(Float SNR, int seed) {
        super();
        
        this.SNR = SNR;
        this.seed = seed;
    }

    public TransmetteurAnalogiqueBruite(Float SNR) {
        this(SNR, (int) (Math.random() * 1024));
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

        float puissance_signal = Tool.getPuissance(this.informationRecue);
        float puissance_bruit = 0;

        if (this.SNR != null) {
            puissance_bruit = puissance_signal / this.SNR;
        }
        //Float[] output = new Float[this.informationRecue.nbElements()];
        //*
        int nbEl = this.informationRecue.nbElements();
        //*/
        /* Génération du Bruit */
        SourceBruitGaussien bruit = new SourceBruitGaussien(nbEl, puissance_bruit, seed);
        bruit.emettre();
        this.informationBruit = bruit.getInformationEmise();

        System.out.println("Puissance signal recu : " + puissance_signal + " / SNR canal " + this.SNR + " / Puissance du bruit à appliquer " + puissance_bruit + " / Puissance réel du bruit " + Tool.getPuissance(this.informationBruit));

        this.informationEmise = Array.SumArrays(informationRecue, informationBruit);
        
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }

}
