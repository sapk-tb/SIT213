package emetteurs;

import sources.*;
import destinations.*;
import information.*;

import java.util.*;

/**
 * Classe Abstraite d'un composant emetteur d'informations dont les �l�ments
 * sont de type R en entr�e et de type E en sortie; l'entr�e de emetteur
 * impl�mente l'interface DestinationInterface, la sortie de emetteur
 * impl�mente l'interface SourceInterface
 *
 * @author Antoine GIRARD
 * @author C�dric HERZOG
 * @author Pierrick CHOVELON
 * @author M�lanie CORRE
 */
public abstract class Emetteur<R, E> implements DestinationInterface<R>, SourceInterface<E> {

    /**
     * la liste des composants destination connect�s en sortie de emetteur
     */
    protected LinkedList<DestinationInterface<E>> destinationsConnectees;

    /**
     * l'information re�ue en entr�e de emetteur
     */
    protected Information<R> informationRecue;

    /**
     * l'information �mise en sortie de emetteur
     */
    protected Information<E> informationEmise;

    /**
     * un constructeur factorisant les initialisations communes aux r�alisations
     * de la classe abstraite Transmetteur
     */
    public Emetteur() {
        destinationsConnectees = new LinkedList<DestinationInterface<E>>();
        informationRecue = null;
        informationEmise = null;
    }

    /**
     * retourne la derni�re information re�ue en entr�e de emetteur
     *
     * @return une information
     */
    @Override
    public Information<R> getInformationRecue() {
        return this.informationRecue;
    }

    /**
     * retourne la derni�re information �mise en sortie de emetteur
     *
     * @return une information
     */
    @Override
    public Information<E> getInformationEmise() {
        return this.informationEmise;
    }

    /**
     * connecte une destination � la sortie de emetteur
     *
     * @param destination la destination � connecter
     */
    @Override
    public void connecter(DestinationInterface<E> destination) {
        destinationsConnectees.add(destination);
    }

    /**
     * d�connecte une destination de la sortie de emetteur
     *
     * @param destination la destination � d�connecter
     */
    public void deconnecter(DestinationInterface<E> destination) {
        destinationsConnectees.remove(destination);
    }

    /**
     * re�oit une information. Cette m�thode, en fin d'ex�cution, appelle la
     * m�thode �mettre.
     *
     * @param information l'information re�ue
     */
    @Override
    public abstract void recevoir(Information<R> information) throws InformationNonConforme;

    /**
     * �met l'information construite par l'emetteur
     */
    @Override
    public abstract void emettre() throws InformationNonConforme;
}
