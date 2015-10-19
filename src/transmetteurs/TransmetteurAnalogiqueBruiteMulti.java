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
public class TransmetteurAnalogiqueBruiteMulti extends Transmetteur<Float, Float> {

    protected Information<Float> informationBruit;
    //Décalage en échantillions
    private final Integer[] dt;
    //Amplitude relative
    private final Float[] ar;
    private Float SNR = null;
    private int seed;

    public TransmetteurAnalogiqueBruiteMulti(Integer[] dt, Float[] ar, Float SNR) throws Exception {
        this(dt, ar, SNR, (int) (Math.random() * 1024));
    }

    public TransmetteurAnalogiqueBruiteMulti(Integer[] dt, Float[] ar, Float SNR, int seed) throws Exception {
        super();
        if (dt.length != ar.length) {
            throw new Exception("Arguments de multiple trajet donnée invalide");
        }

        
        this.dt = dt;
        this.ar = ar;
        this.SNR = SNR;
        this.seed = seed;
    }

    /*public TransmetteurAnalogiqueBruiteMulti(Float SNR) {
     this(SNR, (int) (Math.random() * 1024));
     }*/
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
     *
     * @throws InformationNonConforme Information nulle
     */
    @Override
    public void emettre() throws InformationNonConforme {
        int max = 0;
        for (int i = 0; i < dt.length; i++) {
            if (dt[i] > max) {
                max = dt[i];
            }
        }

        Float[] recu = new Float[informationRecue.nbElements()];
        informationRecue.toArray(recu);
        this.informationEmise = new Information<Float>(recu);
        //System.out.println("nbEch dans sortie : " + this.informationEmise.nbElements());

        for (int i = 0; i < dt.length; i++) {
            if (ar[i] == 0) {
                continue;//On continue car le signal est null
            }
            //System.out.println("Generating trajet n°" + i);
            Information<Float> temp = new Information<Float>(recu);
            for (int j = 0; j < dt[i]; j++) {
                temp.addAt(0, 0f);
            }
            temp = ArrayTool.factArrays(temp, ar[i]);
            this.informationEmise = ArrayTool.sumArrays(this.informationEmise, temp);
            //System.out.println("nbEch dans sortie : " + this.informationEmise.nbElements());
        }

        //------------------------Ajout bruit---------------------------
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

        this.informationEmise = ArrayTool.sumArrays(informationEmise, informationBruit);
        //-----------------------Fin ajout bruit---------------------------

        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(this.informationEmise);
        }
    }

}
