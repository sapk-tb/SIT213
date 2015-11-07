package sources;

import information.Information;

/**
 * Classe d'un composant source d'informations fix� dont les �l�ments sont de
 * type Boolean
 *
 * @author Antoine GIRARD
 * @author C�dric HERZOG
 */
public class SourceFixe extends Source<Boolean> {

    /**
    * Un constructeur qui g�n�re les bits bas�s sur messageSimulateur
     * @param messageSimulateur le message qui fixe les bits */
    public SourceFixe(String messageSimulateur) {
        super();
        this.informationGeneree = new Information<>(messageSimulateur.length());

        for (int i = 0; i < messageSimulateur.length(); i++) {
            //Le messageSimulateur est une suite de 0 et 1 (v�rifi� lors du controle des arguments).
            this.informationGeneree.add(messageSimulateur.charAt(i) == '1');
        }
    }
}
