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
    private boolean modeQuick = false;

    public TransmetteurAnalogiqueBruite(Double SNR, int seed, boolean modeQuick) {
        super();

        this.SNR = SNR;
        this.seed = seed;
        this.modeQuick = modeQuick;
    }

    public TransmetteurAnalogiqueBruite(Double SNR, int seed) {
        this(SNR, seed, false);
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
        this.informationRecue = information;
        checkInformationRecue();
        emettre();
    }

    /**
     * Ajoute le bruit au signal
     *
     * @throws information.InformationNonConforme
     */
    protected void addBruit() throws InformationNonConforme {
        checkInformationRecue();
        if (this.SNR != null) { // On a du bruit
            double puissance_signal = Tool.getPuissance(this.informationRecue);
            double puissance_bruit = puissance_signal / this.SNR;
            int nbEl = this.informationRecue.nbElements();

            //SourceBruitGaussien bruit = new SourceBruitGaussien(nbEl, puissance_bruit, seed);
            SourceBruitGaussien bruit = new SourceBruitGaussien(nbEl, puissance_bruit, seed, modeQuick);
            bruit.emettre();
            this.informationBruit = bruit.getInformationEmise();
            System.out.println("Puissance signal recu : " + puissance_signal + " / SNR canal " + this.SNR + " / Puissance du bruit Ã  appliquer " + puissance_bruit + " / Puissance réel du bruit " + Tool.getPuissance(this.informationBruit));

            this.informationEmise = ArrayTool.sumArrays(informationRecue, informationBruit);
        } else { // Le bruit est null
            this.informationEmise = new Information<>(this.informationRecue); //We clone the object to not affect element in amount
        }
    }

    /**
     * Verifie que le l'information recu est valide sinon déclenche une exception de
     * type InformationNonConforme
     *
     * @throws information.InformationNonConforme
     */
    protected void checkInformationRecue() throws InformationNonConforme {
        if (this.informationRecue == null) {
            throw new InformationNonConforme("information recue == null");
        }
    }

    /**
     * Envoie l'informationEmise aux éléments connectés
     *
     * @throws InformationNonConforme
     */
    protected void envoyerAuxSuivants() throws InformationNonConforme {
        for (DestinationInterface<Double> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(this.informationEmise);
        }
    }

    /**
     * émet l'information construite par le transmetteur
     *
     * @throws information.InformationNonConforme
     */
    @Override
    public void emettre() throws InformationNonConforme {

        checkInformationRecue();
        /* Génération du Bruit */
        addBruit();

        envoyerAuxSuivants();
    }

}
