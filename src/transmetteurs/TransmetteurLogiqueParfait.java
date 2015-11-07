package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

/**
 * Classe d'un composant qui transmet des informations de type Boolean sans
 * défaut.
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 */
public class TransmetteurLogiqueParfait extends Transmetteur<Boolean, Boolean> {

    /**
     * reçoit une information. Cette méthode, en fin d'exécution, appelle la
     * méthode emettre.
     *
     * @param information l'information reçue
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
     * émet l'information construite par la transmetteur
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
