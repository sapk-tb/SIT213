   package visualisations;
	
	
   import information.Information;


/** 
 * Classe réalisant l'affichage d'information composée d'élèments réels (double)
 * @author prou
 */
   public class SondeAnalogique extends Sonde <Double> {
    
   
    /**
    * pour construire une sonde analogique  
    * @param nom  le nom de la fenêtre d'affichage
    */
      public SondeAnalogique(String nom) {
         super(nom);
      }
   
   
   	 
      public void recevoir (Information <Double> information) { 
         informationRecue = information;
         int nbElements = information.nbElements();
         double [] table = new double[nbElements];
         int i = 0;
         for (double f : information) {
            table[i] = f;
            i++;
         }
         new VueCourbe (table, nom); 
      }
   	 
   	
   
   
   }