package recepteurs;

import sources.*;
import destinations.*;
import information.*;

import java.util.*;

/**
 * Classe Abstraite d'un composant recepteur d'informations dont les élèments
 * sont de type R en entrée et de type E en sortie; l'entrée du recepteur
 * implémente l'interface DestinationInterface, la sortie du recepteur
 * implémente l'interface SourceInterface
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 * @author Pierrick CHOVELON
 * @author Mélanie CORRE
 */
public abstract class Recepteur<R, E> implements DestinationInterface<R>, SourceInterface<E> {

    /**
     * la liste des composants destination connectés en sortie du recepteur
     */
    protected LinkedList<DestinationInterface<E>> destinationsConnectees;

    /**
     * l'information reçue en entrée du recepteur
     */
    protected Information<R> informationRecue;

    /**
     * l'information émise en sortie du recepteur
     */
    protected Information<E> informationEmise;

    /**
     * un constructeur factorisant les initialisations communes aux réalisations
     * de la classe abstraite Transmetteur
     */
    public Recepteur() {
        destinationsConnectees = new LinkedList<DestinationInterface<E>>();
        informationRecue = null;
        informationEmise = null;
    }

    /**
     * retourne la dernière information reçue en entrée du recepteur
     *
     * @return une information
     */
    public Information<R> getInformationRecue() {
        return this.informationRecue;
    }

    /**
     * retourne la dernière information émise en sortie du recepteur
     *
     * @return une information
     */
    public Information<E> getInformationEmise() {
        return this.informationEmise;
    }

    /**
     * connecte une destination à la sortie du recepteur
     *
     * @param destination la destination à connecter
     */
    public void connecter(DestinationInterface<E> destination) {
        destinationsConnectees.add(destination);
    }

    /**
     * déconnecte une destination de la la sortie du recepteur
     *
     * @param destination la destination à déconnecter
     */
    public void deconnecter(DestinationInterface<E> destination) {
        destinationsConnectees.remove(destination);
    }

    /**
     * reçoit une information. Cette méthode, en fin d'exécution, appelle la
     * méthode émettre.
     *
     * @param information l'information reçue
     */
    public abstract void recevoir(Information<R> information) throws InformationNonConforme;

    /**
     * émet l'information construite par le recepteur
     */
    public abstract void emettre() throws InformationNonConforme;
}
