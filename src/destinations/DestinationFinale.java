package destinations;

import information.Information;
import information.InformationNonConforme;

/**
 * Classe d'un composant qui recoit des informations de type Boolean et les stockes.
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 */
public class DestinationFinale extends Destination<Boolean> {

    @Override
    public void recevoir(Information<Boolean> information) throws InformationNonConforme {
        this.informationRecue = information;
    }

}
