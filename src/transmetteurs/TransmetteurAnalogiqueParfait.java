package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

/**
 * Classe d'un composant qui transmet des informations de type Float sans
 * défaut.
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 */
public class TransmetteurAnalogiqueParfait extends Transmetteur<Float, Float> {

    /**
     * reçoit une information. Cette méthode, en fin d'exécution, appelle la
     * méthode emettre.
     *
     * @param information l'information reçue
     * @throws information.InformationNonConforme  Quand l'information est invalide
     */
    @Override
    public void recevoir(Information<Float> information) throws InformationNonConforme {
        if(information == null){
            throw new InformationNonConforme("information recue == null");
        }
        this.informationRecue = information;
        emettre();
    }

    private void envoyerAuxSuivants() throws InformationNonConforme {
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(this.informationEmise);
        }
    }
    /**
     * émet l'information construite par la transmetteur
     */
    @Override
    public void emettre() throws InformationNonConforme {
        this.informationEmise = this.informationRecue;
        envoyerAuxSuivants();
    }

}
