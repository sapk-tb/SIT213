package transducteurs;

import java.util.LinkedList;

import sources.SourceInterface;
import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;


/** 
 * Classe permettant le codage de source dans la chaine de transmission
 * @author Pierrick
 * @param <R>
 */

public class TransducteurEmetteur implements DestinationInterface<Boolean>, SourceInterface<Boolean> {

	/** 
	 * la liste des composants destination connectés 
	 */
	protected LinkedList <DestinationInterface <Boolean>> destinationsConnectees;

	/** 
	 * l'information générée par la source qui est reçue par le transducteur
	 */
	protected Information <Boolean>  informationRecue;

	/** 
	 * l'information générée par le transducteur
	 */
	protected Information <Boolean>  informationGeneree;


	/** 
	 * l'information émise par le transducteur après ajout du codage de source
	 */
	protected Information <Boolean>  informationEmise;

	/**
	 * Constructeur de TransducteurEmetteur
	 */
	public TransducteurEmetteur(){


	}
	/**
	 * retourne la dernière information émise par le transducteur
	 * @return une information   
	 */
	public Information <Boolean>  getInformationEmise() {
		return this.informationEmise;
	}
	
	/**
	 * retourne la dernière information recue par le transducteur
	 * @return une information   
	 */
	public Information <Boolean>  getInformationRecue() {
		return this.informationRecue;
	}

	/**
	 * connecte une  destination au transducteur 
	 * @param destination la destination à connecter
	 */
	public void connecter (DestinationInterface <Boolean> destination) {
		destinationsConnectees.add(destination); 
	}


	/**
	 * déconnecte une destination du transducteur
	 * @param destination la destination à déconnecter
	 */
	public void deconnecter (DestinationInterface <Boolean> destination) {
		destinationsConnectees.remove(destination); 
	}
	
	/**
	 * reçoit l'information initiale à laquelle il faut rajouter le codage de source
	 */
	public   void recevoir(Information<Boolean> info){
			 			      
		this.informationRecue = info;
	}

	/**
	 * émet l'information générée  
	 */
	public   void emettre() throws InformationNonConforme {
		
		int nbElements = informationRecue.nbElements();
		informationGeneree = new Information<Boolean>(informationRecue.nbElements()*3);//L'information envoyée sera 3 fois plus longue
		
		//pour tous les élements de l'information recue
		for(int i = 0; i < nbElements; i++)
		{
			if(informationRecue.iemeElement(i) == true)//si 1 --> 101 : true false true
			{
				informationGeneree.add(true);
				informationGeneree.add(false);
				informationGeneree.add(true);
			}
			else //si 0 --> 010 : false true false
			{
				informationGeneree.add(false);
				informationGeneree.add(true);
				informationGeneree.add(false);
			}
		}
		
		// émission vers les composants connectés  
		for (DestinationInterface <Boolean> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationGeneree);
		}
		this.informationEmise = informationGeneree;   			 			      

	}

}
