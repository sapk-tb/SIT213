package sources;

import information.Information;

/**
 * Classe d'un composant source d'informations fixé dont les élèments sont de
 * type Boolean
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 */
public class SourceFixe extends Source<Boolean> {

    /**
    * Un constructeur qui génère les bites basé sur messageSimulateur
     * @param messageSimulateur le message qui fixe les bites */
    public SourceFixe(String messageSimulateur) {
        super();
        this.informationGeneree = new Information<Boolean>();

        for (int i = 0; i < messageSimulateur.length(); i++) {
            //Le messageSimulateur est une suite de 0 et 1 (vérifié lors du controle des arguments).
            this.informationGeneree.add(messageSimulateur.charAt(i) == '1');
        }
    }
}
