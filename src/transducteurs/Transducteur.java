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
     * la liste des composants destination connect�s en sortie du transducteur
     */
    protected LinkedList<DestinationInterface<E>> destinationsConnectees;

    /**
     * l'information re�ue en entr�e du transducteur
     */
    protected Information<R> informationRecue;

    /**
     * l'information �mise en sortie du transcducteur
     */
    protected Information<E> informationEmise;
    
	/** 
	 * l'information g�n�r�e par le transducteur r�cepteur
	 */
	protected Information <Boolean>  informationGeneree;

    /**
     * un constructeur factorisant les initialisations communes aux r�alisations
     * de la classe abstraite Transmetteur
     */
    public Transducteur() {
        destinationsConnectees = new LinkedList<DestinationInterface<E>>();
        informationRecue = null;
        informationEmise = null;
        informationGeneree = null;
    }

    /**
     * retourne la derni�re information re�ue en entr�e du transducteur
     *
     * @return une information
     */
    public Information<R> getInformationRecue() {
        return this.informationRecue;
    }

    /**
     * retourne la derni�re information �mise en sortie du  transducteur
     *
     * @return une information
     */
    public Information<E> getInformationEmise() {
        return this.informationEmise;
    }

    /**
     * connecte une destination � la sortie du  transducteur
     *
     * @param destination la destination � connecter
     */
    public void connecter(DestinationInterface<E> destination) {
        destinationsConnectees.add(destination);
    }

    /**
     * d�connecte une destination de la sortie du  transducteur
     *
     * @param destination la destination � d�connecter
     */
    public void deconnecter(DestinationInterface<E> destination) {
        destinationsConnectees.remove(destination);
    }

    /**
     * re�oit une information. Cette m�thode, en fin d'ex�cution, appelle la
     * m�thode �mettre.
     * @throws InformationNonConforme
     * @param information l'information re�ue
     */
    public abstract void recevoir(Information<R> information) throws InformationNonConforme;

    /**
     * �met l'information construite par le recepteur
     */
    public abstract void emettre() throws InformationNonConforme;

}
