package transmetteurs;

/**
 * Classe d'un composant qui transmet des informations de type Float avec des trajets multiples mais sans défaut.
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 */
public class TransmetteurAnalogiqueParfaitMulti extends TransmetteurAnalogiqueBruiteMulti {

    public TransmetteurAnalogiqueParfaitMulti(Integer[] dt, Float[] ar) throws Exception {
        super(dt, ar, null);
    }

}
