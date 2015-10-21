package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;
import sources.SourceBruitGaussien;
import tools.ArrayTool;
import tools.Tool;

/**
 * Classe d'un composant qui transmet des informations de type Double sans
 * défaut.
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 */
public class TransmetteurAnalogiqueBruite extends Transmetteur<Double, Double> {

    /**
     * l'information reçue en entrée du transmetteur
     */
    protected Information<Double> informationBruit;
    protected Double SNR = null;
    protected int seed;

    public TransmetteurAnalogiqueBruite(Double SNR, int seed) {
        super();

        this.SNR = SNR;
        this.seed = seed;
    }

    public TransmetteurAnalogiqueBruite(Double SNR) {
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
    public void recevoir(Information<Double> information) throws InformationNonConforme {
        if (information == null) {
            throw new InformationNonConforme("information recue == null");
        }
        this.informationRecue = information;
        emettre();
    }

    /**
     * Ajoute le bruit au signal
     *
     * @throws information.InformationNonConforme
     */
    protected void addBruit() throws InformationNonConforme {
        if (this.SNR != null) { // On a du bruit
            double puissance_signal = Tool.getPuissance(this.informationRecue);
            double puissance_bruit = puissance_signal / this.SNR;
            int nbEl = this.informationRecue.nbElements();

            SourceBruitGaussien bruit = new SourceBruitGaussien(nbEl, puissance_bruit, seed);
            bruit.emettre();
            this.informationBruit = bruit.getInformationEmise();
            System.out.println("Puissance signal recu : " + puissance_signal + " / SNR canal " + this.SNR + " / Puissance du bruit à appliquer " + puissance_bruit + " / Puissance réel du bruit " + Tool.getPuissance(this.informationBruit));

            this.informationEmise = ArrayTool.sumArrays(informationRecue, informationBruit);
        } else { // Le bruit est null
            this.informationEmise = new Information<>(this.informationRecue); //We clone the object to not affect element in amount
        }
    }

    /**
     * Envoie l'informationEmise aux élément connectés
     *
     * @throws InformationNonConforme
     */
    protected void envoyerAuxSuivants() throws InformationNonConforme {
        for (DestinationInterface<Double> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(this.informationEmise);
        }
    }

    /**
     * émet l'information construite par la transmette
     * @throws information.InformationNonConforme
     */
    @Override
    public void emettre() throws InformationNonConforme {

        /* Génération du Bruit */
        addBruit();

        envoyerAuxSuivants();
    }

}
