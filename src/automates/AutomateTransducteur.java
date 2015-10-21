package automates;

/**
 * Classe de l'automate qui est utilisé par le transcodeur récepteur pour décoder le message.
 * @author pierrick
 */
public class AutomateTransducteur {

	/**
	 * méthode qui test si le tableau de boolean passé en paramètre est valide ou nono
	 */
	public void AutomateTransducteur(){
		
	}
	
	public boolean accepte(boolean tab[]){
		int etat = 0; 
		int etatDepart = 0; 
		// 2 états possibles :
		// - 1 : valeur 1
		// - 0 : valeur 0
		int index = 0;
		int nbElem = 3;
		boolean booleanCourant = false; //élément courant 
		
		if(tab[0] == true)//premier caractère = à 1
		{
			etatDepart = 1; 
		}
		else //si false = 0
		{
			etatDepart = 0;
		}
		
		index++;
		etat = etatDepart;
		
		while(index != nbElem){
		
			if((etat == 0) && (tab[index] == true))//passage d'un 0(false) à un 1(true)
			{
				etat = 1;
			}
			else if( (etat == 1) && (tab[index] == false))//passage d'un 1(true) à un 0(false)
			{
				etat = 0;
			}
			else
			{
				return false;
			}
			
			index++;
		}
			
		if (etat == etatDepart) //si on est revenu au départ
		{
			return true;
		}
		return false;//au cas où
	}
	
	/*
	//utilisé pour tester
	public static void main(String args[]){
		
		AutomateTransducteur at = new AutomateTransducteur();
		boolean tab[] = new boolean[3];
		
		System.out.println(at.accepte(tab));
		
	}*/
	
}


