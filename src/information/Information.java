
package information;


   import java.util.*;

/** 
 *  
 * @author prou
 */	
   public  class Information <T>  implements Iterable <T> {
   
   
      private LinkedList <T> content;
   
   
   /**
    * pour construire une information vide
    */
      public Information() {
         this.content = new LinkedList <T> (); 
      }
   
   	
   
   /**
    * pour construire à partir d'un tableau de T une information
    * @param content le tableau d'élèments pour initialiser l'information construite
    */
      public Information(T [] content) {
         this.content = new LinkedList <T> (); 
         for (int i = 0; i < content.length; i++) {
            this.content.addLast(content[i]);
         }
      }
   
   
   /**
    * pour connaitre le nombre d'élèments d'une information
    * @return le nombre d'élèments de l'information
    */
      public int nbElements() {
         return this.content.size();
      }
   
   /**
    * pour renvoyer un élèment d'une information
     * @param i indice de l'élèmetn à retourné
    * @return le ieme élèment de l'information
    */
      public T iemeElement(int i) {
         return this.content.get(i);
      }

   
   /**
    * pour modifier le ième élèment d'une information
     * @param i indice de l'élèmetn à modifier
     * @param v valeur à modifier
    */
      public void setIemeElement(int i, T v) {
         this.content.set(i, v);
      }
   
   /**
    * pour ajouter un élèment à la fin de l'information 
    * @param valeur  l'élèment à rajouter
    */
      public void add(T valeur) {
         this.content.add(valeur);
      }
   
   
   /**
    * pour comparer l'information courante avec une autre information
    * @param o  l'information  avec laquelle se comparer
    * @return "true" si les 2 informations contiennent les mêmes élèments aux mêmes places; "false" dans les autres cas
    */	 
      @SuppressWarnings("unchecked")
      public boolean equals(Object o) {
         if (!(o instanceof Information))
            return false;
         Information <T> information =  (Information <T>) o;
         if (this.nbElements() != information.nbElements())
            return false;
         for (int i = 0; i < this.nbElements(); i++) {
            if (!this.iemeElement(i).equals(information.iemeElement(i)))
               return false;
         }
         return true;
      }
   
   /**
    * pour afficher une information 
    */
      public String toString() {
         String s = "";
         for (int i = 0; i < this.nbElements(); i++) {
            s += " " + this.iemeElement(i);
         }
         return s;
      }
   
   /**
    * pour utilisation du "for each" 
    */
      public Iterator <T> iterator() {
         return content.iterator();
      }
   
   }