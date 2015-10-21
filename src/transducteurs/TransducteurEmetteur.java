package transducteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;


/** 
 * Classe permettant le codage de source dans la chaine de transmission
 * @author Pierrick
 * @param <R>
 */

public class TransducteurEmetteur extends Transducteur<Boolean,Boolean> {

	/**
	 * Constructeur de TransducteurEmetteur
	 */
	public TransducteurEmetteur(){
		super();

	}
	
	/**
	 * reçoit l'information initiale à laquelle il faut rajouter le codage de source
	 * @throws InformationNonConforme 
	 */
	public   void recevoir(Information<Boolean> info) throws InformationNonConforme{
			 			      
		this.informationRecue = info;
		emettre();
	}

	/**
	 * émet l'information générée  
	 */
	public   void emettre() throws InformationNonConforme {
		 if (informationRecue == null) {
	            throw new InformationNonConforme("informationRecue == null");
	        }
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
