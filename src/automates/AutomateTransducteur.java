package automates;

/**
 * Classe de l'automate utilis� par le transcodeur r�cepteur pour d�coder le message.
 * @author Pierrick Chovelon
 */
public class AutomateTransducteur {


	public void AutomateTransducteur(){
		
	}
	
	/**
	 * m�thode qui teste si le tableau de boolean pass� en param�tre est valide ou non
	 * @return validit� du tableau
	 */
	public boolean accepte(boolean tab[]){
		int etat = 0; 
		int etatDepart = 0; 
		// 2 �tats possibles :
		// - 1 : valeur 1
		// - 0 : valeur 0
		int index = 0;
		int nbElem = 3;
		boolean booleanCourant = false; //�l�ment courant 
		
		if(tab[0] == true)//premier caract�re �gal � 1
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
		
			if((etat == 0) && (tab[index] == true))//passage d'un 0(false) � un 1(true)
			{
				etat = 1;
			}
			else if( (etat == 1) && (tab[index] == false))//passage d'un 1(true) � un 0(false)
			{
				etat = 0;
			}
			else
			{
				return false;
			}
			
			index++;
		}
			
		if (etat == etatDepart) //si on est revenu au d�part
		{
			return true;
		}
		return false;//au cas o�
	}
	
	/*
	//utilis� pour tester
	public static void main(String args[]){
		
		AutomateTransducteur at = new AutomateTransducteur();
		boolean tab[] = new boolean[3];
		
		System.out.println(at.accepte(tab));
		
	}*/
	
}


