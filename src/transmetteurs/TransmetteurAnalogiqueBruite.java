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
        float puissance_bruit = 0;
        
        if(this.SNR != 0f){
            puissance_bruit = Tool.getPuissance(this.informationRecue) / this.SNR;
        }
        
        SourceBruitGaussien bruit = new SourceBruitGaussien(this.informationRecue.nbElements(), puissance_bruit);
        bruit.emettre();
        Information<Float> InfBruit = bruit.getInformationEmise();

        this.informationEmise = this.informationRecue;
        System.out.println("Puissance signal recu : "+Tool.getPuissance(this.informationRecue) + " / SNR canal " + this.SNR + " / Puissance du bruit à appliquer " + puissance_bruit + " / Puissance réel du bruit " + Tool.getPuissance(InfBruit));
        for (int i = 0; i < this.informationRecue.nbElements(); i++) {
            //System.out.println(this.informationEmise.iemeElement(i) + "/" + InfBruit.iemeElement(i));
            this.informationEmise.setIemeElement(i, this.informationEmise.iemeElement(i) + InfBruit.iemeElement(i));
        }

        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationRecue);
        }
    }

}
