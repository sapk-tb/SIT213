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
        float puissance_bruit = 0;

        if (this.SNR != 0f) {
            puissance_bruit = Tool.getPuissance(this.informationRecue) / this.SNR;
        }

        SourceBruitGaussien bruit = new SourceBruitGaussien(this.informationRecue.nbElements(), puissance_bruit);
        bruit.emettre();
        this.informationBruit = bruit.getInformationEmise();

        //this.informationEmise = new Information<Float>();
        //Float[] output = new Float[this.informationRecue.nbElements()];
        //*
        int nbEl = this.informationRecue.nbElements();
        Float[] output = new Float[nbEl];
        this.informationRecue.toArray(output);
        Float[] b = new Float[nbEl];
        this.informationBruit.toArray(b);
        //*/
        System.out.println("Puissance signal recu : " + Tool.getPuissance(this.informationRecue) + " / SNR canal " + this.SNR + " / Puissance du bruit à appliquer " + puissance_bruit + " / Puissance réel du bruit " + Tool.getPuissance(this.informationBruit));
        for (int i = 0; i < this.informationRecue.nbElements(); i++) {
        //for (int i = 0; i < nbEl; i++) {
            //this.informationEmise.add(this.informationRecue.iemeElement(i) + this.informationBruit.iemeElement(i));
            output[i] += b[i];
        }
        this.informationEmise = new Information<Float>(output);
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }

}
