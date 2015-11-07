package transducteurs;

import information.Information;
import information.InformationNonConforme;

import java.util.LinkedList;

import sources.SourceInterface;
import destinations.DestinationInterface;

/**
 * 
 * @author Pierrick CHOVELON
 * Classe abstraite qui sert de base pour les classes TransducteurEmeteur et TransducteurRecepteur
 * @param <R>
 * @param <E>
 */
public abstract class Transducteur <R, E> implements DestinationInterface<R>, SourceInterface<E>  {
	
	/**
     * la liste des composants destination connectés en sortie du transducteur
     */
    protected LinkedList<DestinationInterface<E>> destinationsConnectees;

    /**
     * l'information reçue en entrée du transducteur
     */
    protected Information<R> informationRecue;

    /**
     * l'information émise en sortie du transcducteur
     */
    protected Information<E> informationEmise;
    
	/** 
	 * l'information générée par le transducteur récepteur
	 */
	protected Information <Boolean>  informationGeneree;

    /**
     * un constructeur factorisant les initialisations communes aux réalisations
     * de la classe abstraite Transmetteur
     */
    public Transducteur() {
        destinationsConnectees = new LinkedList<DestinationInterface<E>>();
        informationRecue = null;
        informationEmise = null;
        informationGeneree = null;
    }

    /**
     * retourne la dernière information reçue en entrée du transducteur
     *
     * @return une information
     */
    public Information<R> getInformationRecue() {
        return this.informationRecue;
    }

    /**
     * retourne la dernière information émise en sortie du  transducteur
     *
     * @return une information
     */
    public Information<E> getInformationEmise() {
        return this.informationEmise;
    }

    /**
     * connecte une destination à la sortie du  transducteur
     *
     * @param destination la destination à connecter
     */
    public void connecter(DestinationInterface<E> destination) {
        destinationsConnectees.add(destination);
    }

    /**
     * déconnecte une destination de la sortie du  transducteur
     *
     * @param destination la destination à déconnecter
     */
    public void deconnecter(DestinationInterface<E> destination) {
        destinationsConnectees.remove(destination);
    }

    /**
     * reçoit une information. Cette méthode, en fin d'exécution, appelle la
     * méthode émettre.
     * @throws InformationNonConforme
     * @param information l'information reçue
     */
    public abstract void recevoir(Information<R> information) throws InformationNonConforme;

    /**
     * émet l'information construite par le recepteur
     */
    public abstract void emettre() throws InformationNonConforme;

}
