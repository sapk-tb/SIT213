package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

/**
 * Classe d'un composant qui transmet des informations de type Boolean sans
 * defaut.
 *
 * @author Antoine GIRARD
 * @author Cedric HERZOG
 */
public class TransmetteurLogiqueParfait extends Transmetteur<Boolean, Boolean> {

    /**
     * reeoit une information. Cette methode, en fin d'execution, appelle la
     * methode emettre.
     *
     * @param information l'information recue
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
     * emet l'information construite par la transmetteur
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
