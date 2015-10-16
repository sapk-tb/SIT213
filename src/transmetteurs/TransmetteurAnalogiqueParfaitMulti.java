package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;
import tools.ArrayTool;

/**
 * Classe d'un composant qui transmet des informations de type Float sans
 * défaut.
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 */
public class TransmetteurAnalogiqueParfaitMulti extends Transmetteur<Float, Float> {

    //Décalage en échantillions
    private final Integer[] dt;
    //Amplitude relative
    private final Float[] ar;

    public TransmetteurAnalogiqueParfaitMulti(Integer[] dt, Float[] ar) throws Exception {
        super();
        if (dt.length != ar.length) {
            throw new Exception("Arguments de multiple trajet donnée invalide");
        }
        this.dt = dt;
        this.ar = ar;
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
            //System.out.println(this.informationEmise.toString());
            //System.out.println("nbEch dans sortie : " + this.informationEmise.nbElements());
        }

        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(this.informationEmise);
        }
    }

}
