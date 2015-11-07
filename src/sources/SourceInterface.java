
package sources;

	import information.*;
	import destinations.DestinationInterface;

/** 
 * Interface d'un composant ayant le comportement d'une source d'informations dont les elements sont de type T 
 * @author prou
 * @param <T> Le format de donnees de la source
 */
    public interface SourceInterface <T>  {
   
   /**
    * pour obtenir la derniere information emise par une source.
    * @return une information   
    */
       public Information <T>  getInformationEmise();
   
   /**
    * pour connecter une  destination a la source 
    * @param destination  la destination a connecter
	 */
       public void connecter (DestinationInterface <T> destination);
   
   /**
    * pour emettre l'information  contenue dans une source
     * @throws information.InformationNonConforme Decrit un information null ou non valide
    */
       public void emettre() throws InformationNonConforme; 
   
   
   }