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
public class TransmetteurAnalogiqueParfaitMulti extends TransmetteurAnalogiqueBruiteMulti {

    public TransmetteurAnalogiqueParfaitMulti(Integer[] dt, Float[] ar) throws Exception {
        super(dt, ar, null);
    }

}
