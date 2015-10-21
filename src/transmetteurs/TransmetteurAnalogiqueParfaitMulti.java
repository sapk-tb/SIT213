package transmetteurs;

/**
 * Classe d'un composant qui transmet des informations de type Double avec des
 * trajets multiples mais sans défaut.
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 */
public class TransmetteurAnalogiqueParfaitMulti extends TransmetteurAnalogiqueBruiteMulti {

    public TransmetteurAnalogiqueParfaitMulti(Integer[] dt, Double[] ar) throws Exception {
        super(dt, ar, null);
    }

}
