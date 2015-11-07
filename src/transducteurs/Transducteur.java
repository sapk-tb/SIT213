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
     * la liste des composants destination connectes en sortie du transducteur
     */
    protected LinkedList<DestinationInterface<E>> destinationsConnectees;

    /**
     * l'information recue en entree du transducteur
     */
    protected Information<R> informationRecue;

    /**
     * l'information emise en sortie du transcducteur
     */
    protected Information<E> informationEmise;
    
	/** 
	 * l'information generee par le transducteur recepteur
	 */
	protected Information <Boolean>  informationGeneree;

    /**
     * un constructeur factorisant les initialisations communes aux realisations
     * de la classe abstraite Transmetteur
     */
    public Transducteur() {
        destinationsConnectees = new LinkedList<DestinationInterface<E>>();
        informationRecue = null;
        informationEmise = null;
        informationGeneree = null;
    }

    /**
     * retourne la derniere information recue en entree du transducteur
     *
     * @return une information
     */
    public Information<R> getInformationRecue() {
        return this.informationRecue;
    }

    /**
     * retourne la derniere information emise en sortie du  transducteur
     *
     * @return une information
     */
    public Information<E> getInformationEmise() {
        return this.informationEmise;
    }

    /**
     * connecte une destination a la sortie du  transducteur
     *
     * @param destination la destination a connecter
     */
    public void connecter(DestinationInterface<E> destination) {
        destinationsConnectees.add(destination);
    }

    /**
     * deconnecte une destination de la sortie du  transducteur
     *
     * @param destination la destination a deconnecter
     */
    public void deconnecter(DestinationInterface<E> destination) {
        destinationsConnectees.remove(destination);
    }

    /**
     * reeoit une information. Cette methode, en fin d'execution, appelle la
     * methode emettre.
     * @throws InformationNonConforme
     * @param information l'information recue
     */
    public abstract void recevoir(Information<R> information) throws InformationNonConforme;

    /**
     * emet l'information construite par le recepteur
     */
    public abstract void emettre() throws InformationNonConforme;

}
