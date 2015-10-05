package destinations;

	import information.*;


/** 
 * Interface d'un composant ayant le comportement d'une destination d'informations dont les élèments sont de type T 
 * @author prou
 */
    public  interface DestinationInterface <T>  {   
   
   /**
    * pour obtenir la dernière information reçue par une destination.
    * @return une information   
    */  
       public Information <T>  getInformationRecue(); 
   	 
   /**
    * pour recevoir une information  de la source qui nous est connectée 
    * @param information  l'information  à recevoir
     * @throws information.InformationNonConforme Décrit un information null ou non valide
    */
       public void recevoir(Information <T> information) throws InformationNonConforme;

   	    
   
   }