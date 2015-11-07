package transmetteurs;

/**
 * Classe d'un composant qui transmet des informations de type Double avec des
 * trajets multiples mais sans d�faut.
 *
 * @author Antoine GIRARD
 * @author C�dric HERZOG
 */
public class TransmetteurAnalogiqueParfaitMulti extends TransmetteurAnalogiqueBruiteMulti {

    public TransmetteurAnalogiqueParfaitMulti(Integer[] dt, Double[] ar) throws Exception {
        super(dt, ar, null);
    }

}
