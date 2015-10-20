package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;
import sources.SourceBruitGaussien;
import tools.ArrayTool;
import tools.Tool;

/**
 * Classe d'un composant qui transmet des informations de type Float sans
 * défaut.
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 */
public class TransmetteurAnalogiqueBruite extends TransmetteurAnalogiqueParfait {

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
     * Ajoute le bruit au signal
     */
    private void addBruit() throws InformationNonConforme {
        float puissance_signal = Tool.getPuissance(this.informationRecue);
        float puissance_bruit = 0;

        if (this.SNR != null) {
            puissance_bruit = puissance_signal / this.SNR;
        }
        int nbEl = this.informationRecue.nbElements();

        SourceBruitGaussien bruit = new SourceBruitGaussien(nbEl, puissance_bruit, seed);
        bruit.emettre();
        this.informationBruit = bruit.getInformationEmise();
        System.out.println("Puissance signal recu : " + puissance_signal + " / SNR canal " + this.SNR + " / Puissance du bruit à appliquer " + puissance_bruit + " / Puissance réel du bruit " + Tool.getPuissance(this.informationBruit));

        this.informationEmise = ArrayTool.sumArrays(informationRecue, informationBruit);
    }

    /**
     * émet l'information construite par la transmetteur
     */
    @Override
    public void emettre() throws InformationNonConforme {

        /* Génération du Bruit */
        addBruit();
        super.emettre();
    }

}
