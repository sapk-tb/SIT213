package sources;

import information.Information;

/**
 * Classe d'un composant source d'informations fixées dont les éléments sont de
 * type Boolean
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 */
public class SourceFixe extends Source<Boolean> {

    /**
    * Un constructeur qui génère les bits basés sur messageSimulateur
     * @param messageSimulateur le message qui fixe les bits */
    public SourceFixe(String messageSimulateur) {
        super();
        this.informationGeneree = new Information<>(messageSimulateur.length());

        for (int i = 0; i < messageSimulateur.length(); i++) {
            //Le messageSimulateur est une suite de 0 et 1 (vérifié lors du controle des arguments).
            this.informationGeneree.add(messageSimulateur.charAt(i) == '1');
        }
    }
}
