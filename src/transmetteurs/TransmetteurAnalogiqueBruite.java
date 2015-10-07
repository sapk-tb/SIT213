package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;
import sources.SourceBruitGaussien;
import tools.Tool;
import visualisations.VueCourbe;

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
    private float SNR;

    public TransmetteurAnalogiqueBruite(float SNR) {
        super();
        this.SNR = SNR;
    }

    /**
     * reçoit une information. Cette méthode, en fin d'exécution, appelle la
     * méthode emettre.
     *
     * @param information l'information reçue
     */
    @Override
    public void recevoir(Information<Float> information) throws InformationNonConforme {
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
        //Float[] output = new Float[nbEl];
        //this.informationRecue.toArray(output);
        //*/
        /* Génération du Bruit */
        SourceBruitGaussien bruit = new SourceBruitGaussien(nbEl, puissance_bruit);
        bruit.emettre();
        this.informationBruit = bruit.getInformationEmise();
        //Float[] b = new Float[nbEl];
        //this.informationBruit.toArray(b);

        System.out.println("Puissance signal recu : " + puissance_signal + " / SNR canal " + this.SNR + " / Puissance du bruit à appliquer " + puissance_bruit + " / Puissance réel du bruit " + Tool.getPuissance(this.informationBruit));
        //for (int i = 0; i < this.informationRecue.nbElements(); i++) {
        for (int i = 0; i < nbEl; i++) {
            this.informationEmise.add(this.informationRecue.iemeElement(i) + this.informationBruit.iemeElement(i));
            //output[i] += b[i];
            //output[i] = (this.informationRecue.iemeElement(i) + this.informationBruit.iemeElement(i));
        }
        //this.informationEmise = new Information<Float>(output);
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }

}
