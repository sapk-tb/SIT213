package transducteurs;

import java.util.LinkedList;

import automates.AutomateTransducteur;
import sources.SourceInterface;
import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;


/** 
 * Classe permettant le décodage de source dans la chaine de transmission
 * @author Pierrick
 * @param <R>
 */

public class TransducteurRecepteur extends Transducteur<Boolean,Boolean>{

	/**
	 * Constructeur de TransducteurRecepteur
	 */
	public TransducteurRecepteur(){
		super();
		

	}
	
	/**
	 * reçoit l'information initiale à laquelle il faut supprimer le codage de source
	 * @throws InformationNonConforme 
	 */
	public   void recevoir(Information<Boolean> info) throws InformationNonConforme{
			 			      
		this.informationRecue = info;
		emettre();
	}

	/**
	 * émet l'information générée  (décodée)
	 */
	public   void emettre() throws InformationNonConforme {
		 if (informationRecue == null) {
	            throw new InformationNonConforme("informationRecue == null");
	        }
		//déclaration de l'automate pour tester la validité des infos 
		AutomateTransducteur automate = new AutomateTransducteur();
		
		int nbElements = this.informationRecue.nbElements()/3; // --> /3 pour avoir le nombre d'élément de l'information réelle
		boolean tabTest[] = new boolean[3];//contiendra les valeurs des booleans à tester
		
		Boolean tabBoolean[] = new Boolean[nbElements];
		informationGeneree = new Information<Boolean>(tabBoolean);
		
		for(int i = 0; i < nbElements; i++)
		{
			tabTest[0] = this.informationRecue.iemeElement(i*3);
			tabTest[1] = this.informationRecue.iemeElement(i*3 + 1);
			tabTest[2] = this.informationRecue.iemeElement(i*3 + 2);
			
			if(automate.accepte(tabTest))//on test si les 3 caractères correspondants sont corrects (bonne alternance de 0(false) et de 1(true))
			{
				//On peut regarder que le premier boolean car on sait que les 3 sont corrects
				if(tabTest[0] == true) //true 
				{
					this.informationGeneree.setIemeElement(i,true);
				}
				else //false
				{
					this.informationGeneree.setIemeElement(i,false);
				}
				
			}
			else
			{
				//expression : equation de la table de vérité :
				//false false false --> 0
				//false false true --> 1
				//false true false --> 0
				//false true true --> 0
				//true false false --> 1
				//true false true --> 1
				//true true false --> 0
				//true true true --> 1
				
				boolean expression = (!tabTest[1] || tabTest[2]) && (tabTest[0] || !tabTest[1]) && (tabTest[0] || tabTest[2]);
				this.informationGeneree.setIemeElement(i,expression);
			}
		}
		
		// émission vers les composants connectés  
		for (DestinationInterface <Boolean> destinationConnectee : this.destinationsConnectees) {
			destinationConnectee.recevoir(this.informationGeneree);
		}
		this.informationEmise = informationGeneree;   			 			      

	}
	
	
	
	/*
	//utilisé pour tester
	public static void main(String args[]){
		
		boolean tabTest[] = new boolean[3];
		
		tabTest[0] = true;
		tabTest[1] = true;
		tabTest[2] = true;
		
		boolean expression = (!tabTest[1] || tabTest[2]) && (tabTest[0] || !tabTest[1]) && (tabTest[0] || tabTest[2]);

		System.out.println(expression);
		
	}
	*/
}
