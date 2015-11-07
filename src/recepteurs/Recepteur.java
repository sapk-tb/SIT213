package recepteurs;

import sources.*;
import destinations.*;
import information.*;

import java.util.*;

/**
 * Classe Abstraite d'un composant recepteur d'informations dont les elements
 * sont de type R en entree et de type E en sortie; l'entree du recepteur
 * implemente l'interface DestinationInterface, la sortie du recepteur
 * implemente l'interface SourceInterface
 *
 * @author Antoine GIRARD
 * @author Cedric HERZOG
 * @author Pierrick CHOVELON
 * @author Melanie CORRE
 */
public abstract class Recepteur<R, E> implements DestinationInterface<R>, SourceInterface<E> {

    /**
     * la liste des composants destination connectes en sortie du recepteur
     */
    protected LinkedList<DestinationInterface<E>> destinationsConnectees;

    /**
     * l'information recue en entree du recepteur
     */
    protected Information<R> informationRecue;

    /**
     * l'information emise en sortie du recepteur
     */
    protected Information<E> informationEmise;

    /**
     * un constructeur factorisant les initialisations communes aux realisations
     * de la classe abstraite Transmetteur
     */
    public Recepteur() {
        destinationsConnectees = new LinkedList<DestinationInterface<E>>();
        informationRecue = null;
        informationEmise = null;
    }

    /**
     * retourne la derniere information recue en entree du recepteur
     *
     * @return une information
     */
    public Information<R> getInformationRecue() {
        return this.informationRecue;
    }

    /**
     * retourne la derniere information emise en sortie du recepteur
     *
     * @return une information
     */
    public Information<E> getInformationEmise() {
        return this.informationEmise;
    }

    /**
     * connecte une destination a la sortie du recepteur
     *
     * @param destination la destination a connecter
     */
    public void connecter(DestinationInterface<E> destination) {
        destinationsConnectees.add(destination);
    }

    /**
     * deconnecte une destination de la sortie du recepteur
     *
     * @param destination la destination a deconnecter
     */
    public void deconnecter(DestinationInterface<E> destination) {
        destinationsConnectees.remove(destination);
    }

    /**
     * reeoit une information. Cette methode, en fin d'execution, appelle la
     * methode emettre.
     *
     * @param information l'information recue
     */
    public abstract void recevoir(Information<R> information) throws InformationNonConforme;

    /**
     * emet l'information construite par le recepteur
     */
    public abstract void emettre() throws InformationNonConforme;
}
