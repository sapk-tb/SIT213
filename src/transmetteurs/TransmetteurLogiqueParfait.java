package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

/**
 * Classe d'un composant qui transmet des informations de type Boolean sans
 * d�faut.
 *
 * @author Antoine GIRARD
 * @author C�dric HERZOG
 */
public class TransmetteurLogiqueParfait extends Transmetteur<Boolean, Boolean> {

    /**
     * re�oit une information. Cette m�thode, en fin d'ex�cution, appelle la
     * m�thode emettre.
     *
     * @param information l'information re�ue
     * @throws information.InformationNonConforme Quand l'information est invalide
     */
    @Override
    public void recevoir(Information<Boolean> information) throws InformationNonConforme {
        if(information == null){
            throw new InformationNonConforme("information recue == null");
        }
        this.informationRecue = information;
        emettre();
    }

    /**
     * �met l'information construite par la transmetteur
     * @throws information.InformationNonConforme
     */
    @Override
    public void emettre() throws InformationNonConforme {
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationRecue);
        }
        this.informationEmise = this.informationRecue;

    }

}
