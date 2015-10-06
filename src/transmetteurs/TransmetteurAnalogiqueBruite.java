package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;
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
        SourceBruitGaussien bruit = new SourceBruitGaussien(this.informationRecue.nbElements(), Tool.getPuissance(this.informationRecue) / this.SNR);
        bruit.emettre();
        Information<Float> InfBruit = bruit.getInformationEmise();

        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationRecue);
        }
        this.informationEmise = this.informationRecue;
        System.out.println(Tool.getPuissance(this.informationRecue) + " / " + this.SNR + " / " + Tool.getPuissance(this.informationRecue) / this.SNR+ " / " + Tool.getPuissance(InfBruit));
        for (int i = 0; i < this.informationRecue.nbElements(); i++) {
            //System.out.println(this.informationEmise.iemeElement(i) + "/" + InfBruit.iemeElement(i));
            this.informationEmise.setIemeElement(i, this.informationEmise.iemeElement(i) + InfBruit.iemeElement(i));
        }

    }

}
