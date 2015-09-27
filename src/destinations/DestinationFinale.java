package destinations;

import information.Information;
import information.InformationNonConforme;

/**
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
