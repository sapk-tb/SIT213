package emetteurs;

import sources.*;
import destinations.*;
import information.*;

import java.util.*;

/**
 * Classe Abstraite d'un composant emetteur d'informations dont les éléments
 * sont de type R en entrée et de type E en sortie; l'entrée de emetteur
 * implémente l'interface DestinationInterface, la sortie de emetteur
 * implémente l'interface SourceInterface
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 * @author Pierrick CHOVELON
 * @author Mélanie CORRE
 */
public abstract class Emetteur<R, E> implements DestinationInterface<R>, SourceInterface<E> {

    /**
     * la liste des composants destination connectés en sortie de emetteur
     */
    protected LinkedList<DestinationInterface<E>> destinationsConnectees;

    /**
     * l'information reçue en entrée de emetteur
     */
    protected Information<R> informationRecue;

    /**
     * l'information émise en sortie de emetteur
     */
    protected Information<E> informationEmise;

    /**
     * un constructeur factorisant les initialisations communes aux réalisations
     * de la classe abstraite Transmetteur
     */
    public Emetteur() {
        destinationsConnectees = new LinkedList<DestinationInterface<E>>();
        informationRecue = null;
        informationEmise = null;
    }

    /**
     * retourne la dernière information reçue en entrée de emetteur
     *
     * @return une information
     */
    @Override
    public Information<R> getInformationRecue() {
        return this.informationRecue;
    }

    /**
     * retourne la dernière information émise en sortie de emetteur
     *
     * @return une information
     */
    @Override
    public Information<E> getInformationEmise() {
        return this.informationEmise;
    }

    /**
     * connecte une destination à  la sortie de emetteur
     *
     * @param destination la destination à connecter
     */
    @Override
    public void connecter(DestinationInterface<E> destination) {
        destinationsConnectees.add(destination);
    }

    /**
     * déconnecte une destination de la sortie de emetteur
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
    @Override
    public abstract void recevoir(Information<R> information) throws InformationNonConforme;

    /**
     * émet l'information construite par l'emetteur
     */
    @Override
    public abstract void emettre() throws InformationNonConforme;
}
