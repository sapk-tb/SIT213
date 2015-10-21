package transmetteurs;

import information.Information;
import information.InformationNonConforme;
import java.util.logging.Level;
import java.util.logging.Logger;
import tools.ArrayTool;

/**
 * Classe d'un composant qui transmet des informations de type Float sans
 * défaut.
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 */
public class TransmetteurAnalogiqueBruiteMulti extends TransmetteurAnalogiqueBruite {

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
        super(SNR, seed);
        if (dt.length != ar.length) {
            throw new Exception("Arguments de multiple trajet donnée invalide");
        }
        this.dt = dt;
        this.ar = ar;
        this.SNR = SNR;
        this.seed = seed;
    }

    /**
     * émet l'information construite par la transmetteur
     *
     * @throws InformationNonConforme Information nulle
     */
    @Override
    public void emettre() throws InformationNonConforme {

        /* Génération du Bruit */
        addBruit();

        System.out.println("nbEch avant multi-trajet : " + this.informationEmise.nbElements());
        /* Mise en forme pour les multi-trajet */
        Information<Float> infBruite = new Information<>(this.informationEmise);
        for (int i = 0; i < dt.length; i++) {
            if (ar[i] == 0) {
                continue;
            }
            System.out.println("Generating trajet n°" + i + " ( dt : " + dt[i] + ", ar : " + ar[i] + " ) ");
            //TODO check if we should maybe do Information retard = ArrayTool.factArrays(this.informationEmise, ar[i]); 
            Information retard = ArrayTool.factArrays(infBruite, ar[i]); //On génère une information factorisé par l'attenuation
            for (int j = 0; j < dt[i]; j++) {
                retard.addAt(0, 0f); // On ajoute les retards
            }
            System.out.println("Taille du tableau de retard : " + retard.nbElements());
            this.informationEmise = ArrayTool.sumArrays(this.informationEmise, retard);
        }

        System.out.println("nbEch dans sortie : " + this.informationEmise.nbElements());
        envoyerAuxSuivants();
    }

}
