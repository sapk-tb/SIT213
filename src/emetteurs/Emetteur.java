package emetteurs;

import sources.*;
import destinations.*;
import information.*;

import java.util.*;

/**
 * Classe Abstraite d'un composant emetteur d'informations dont les elements
 * sont de type R en entree et de type E en sortie; l'entree de emetteur
 * implemente l'interface DestinationInterface, la sortie de emetteur
 * implemente l'interface SourceInterface
 *
 * @author Antoine GIRARD
 * @author Cedric HERZOG
 * @author Pierrick CHOVELON
 * @author Melanie CORRE
 */
public abstract class Emetteur<R, E> implements DestinationInterface<R>, SourceInterface<E> {

    /**
     * la liste des composants destination connectes en sortie de emetteur
     */
    protected LinkedList<DestinationInterface<E>> destinationsConnectees;

    /**
     * l'information recue en entree de emetteur
     */
    protected Information<R> informationRecue;

    /**
     * l'information emise en sortie de emetteur
     */
    protected Information<E> informationEmise;

    /**
     * un constructeur factorisant les initialisations communes aux realisations
     * de la classe abstraite Transmetteur
     */
    public Emetteur() {
        destinationsConnectees = new LinkedList<DestinationInterface<E>>();
        informationRecue = null;
        informationEmise = null;
    }

    /**
     * retourne la derniere information recue en entree de emetteur
     *
     * @return une information
     */
    @Override
    public Information<R> getInformationRecue() {
        return this.informationRecue;
    }

    /**
     * retourne la derniere information emise en sortie de emetteur
     *
     * @return une information
     */
    @Override
    public Information<E> getInformationEmise() {
        return this.informationEmise;
    }

    /**
     * connecte une destination a la sortie de emetteur
     *
     * @param destination la destination e connecter
     */
    @Override
    public void connecter(DestinationInterface<E> destination) {
        destinationsConnectees.add(destination);
    }

    /**
     * deconnecte une destination de la sortie de emetteur
     *
     * @param destination la destination e deconnecter
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
    @Override
    public abstract void recevoir(Information<R> information) throws InformationNonConforme;

    /**
     * emet l'information construite par l'emetteur
     */
    @Override
    public abstract void emettre() throws InformationNonConforme;
}
