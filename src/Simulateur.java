
   import sources.*;
   import destinations.*;
   import transmetteurs.*;

   import information.*;

   import visualisations.*;

   import java.util.regex.*;
   import java.util.*;
   import java.lang.Math;

	
   import java.io.FileInputStream;
   import java.io.FileNotFoundException;
   import java.io.BufferedWriter;
   import java.io.FileWriter;
   import java.io.IOException;
   import java.io.PrintWriter;


/** La classe Simulateur permet de construire et simuler une chaine de transmission composée d'une Source, d'un nombre variable de Transmetteur(s) et d'une Destination.  
 * @author cousin
 * @author prou
 *
 */
   public class Simulateur {
      	
   /** indique si le Simulateur utilise des sondes d'affichage */
      private          boolean affichage = false;
   /** indique si le Simulateur utilise un message généré de manière aléatoire */
      private          boolean messageAleatoire = true;
   /** indique si le Simulateur utilise un germe pour initialiser les générateurs aléatoires */
      private          boolean aleatoireAvecGerme = false;
   /** la valeur de la semence utilisée pour les générateurs aléatoires */
      private          Integer seed = null;
   /** la longueur du message aléatoire à transmettre si un message n'est pas impose */
      private          int nbBitsMess = 100; 
   /** la chaine de caractères correspondant à m dans l'argument -mess m */
      private          String messageString = "100";
   	
   
   
   	
   /** le  composant Source de la chaine de transmission */
      private			  Source <Boolean>  source = null;
   /** le  composant Transmetteur parfait logique de la chaine de transmission */
      private			  Transmetteur <Boolean, Boolean>  transmetteurLogique = null;
   /** le  composant Destination de la chaine de transmission */
      private			  Destination <Boolean>  destination = null;
   	
   
   /** Le constructeur de Simulateur construit une chaine de transmission composée d'une Source <Boolean>, d'une Destination <Boolean> et de Transmetteur(s) [voir la méthode analyseArguments]...  
   * <br> Les différents composants de la chaine de transmission (Source, Transmetteur(s), Destination, Sonde(s) de visualisation) sont créés et connectés.
   * @param args le tableau des différents arguments.
   *
   * @throws ArgumentsException si un des arguments est incorrect
   *
   */   
      public  Simulateur(String [] args) throws ArgumentsException {
      
      	// analyser et récupérer les arguments
      	
         analyseArguments(args);
      
      	      
      	// A compléter
      	
      		
      }
   
   
   
   /** La méthode analyseArguments extrait d'un tableau de chaines de caractéres les différentes options de la simulation. 
   * Elle met à jour les attributs du Simulateur.
   *
   * @param args le tableau des différents arguments.
   * <br>
   * <br>Les arguments autorisés sont : 
   * <br> 
   * <dl>
   * <dt> -mess m  </dt><dd> m (String) constitué de 7 ou plus digits à 0 | 1, le message à transmettre</dd>
   * <dt> -mess m  </dt><dd> m (int) constitué de 1 à 6 digits, le nombre de bits du message "aléatoire" à transmettre</dd> 
   * <dt> -s </dt><dd> utilisation des sondes d'affichage</dd>
   * <dt> -seed v </dt><dd> v (int) d'initialisation pour les générateurs aléatoires</dd> 
   * <br>
   * <dt> -form f </dt><dd>  codage (String) RZ, NRZR, NRZT, la forme d'onde du signal à transmettre (RZ par défaut)</dd>
   * <dt> -nbEch ne </dt><dd> ne (int) le nombre d'échantillons par bit (ne >= 6 pour du RZ, ne >= 9 pour du NRZT, ne >= 18 pour du RZ,  30 par défaut))</dd>
   * <dt> -ampl min max </dt><dd>  min (float) et max (float), les amplitudes min et max du signal analogique à transmettre ( min < max, 0.0 et 1.0 par défaut))</dd> 
   * <br>
   * <dt> -snr s </dt><dd> s (float) le rapport signal/bruit en dB</dd>
   * <br>
   * <dt> -ti i dt ar </dt><dd> i (int) numero du trajet indirect (de 1 à 5), dt (int) valeur du decalage temporel du ième trajet indirect 
   * en nombre d'échantillons par bit, ar (float) amplitude relative au signal initial du signal ayant effectué le ième trajet indirect</dd>
   * <br>
   * <dt> -transducteur </dt><dd> utilisation de transducteur</dd>
   * <br>
   * <dt> -aveugle </dt><dd> les récepteurs ne connaissent ni l'amplitude min et max du signal, ni les différents trajets indirects (s'il y en a).</dd>
   * <br>
   * </dl>
   * <br> <b>Contraintes</b> :
   * Il y a des interdépendances sur les paramètres effectifs. 
   *
   * @throws ArgumentsException si un des arguments est incorrect.
   *
   */   
      public  void analyseArguments(String[] args)  throws  ArgumentsException {
      		
         for (int i=0;i<args.length;i++){ 
         
              
            if (args[i].matches("-s")){
               affichage = true;
            }
            else if (args[i].matches("-seed")) {
               aleatoireAvecGerme = true;
               i++; 
            	// traiter la valeur associee
               try { 
                  seed =new Integer(args[i]);
               }
                  catch (Exception e) {
                     throw new ArgumentsException("Valeur du parametre -seed  invalide :" + args[i]);
                  }           		
            }
            
            else if (args[i].matches("-mess")){
               i++; 
            	// traiter la valeur associee
               messageString = args[i];
               if (args[i].matches("[0,1]{7,}")) {
                  messageAleatoire = false;
                  nbBitsMess = args[i].length();
               } 
               else if (args[i].matches("[0-9]{1,6}")) {
                  messageAleatoire = true;
                  nbBitsMess = new Integer(args[i]);
                  if (nbBitsMess < 1) 
                     throw new ArgumentsException ("Valeur du parametre -mess invalide : " + nbBitsMess);
               }
               else 
                  throw new ArgumentsException("Valeur du parametre -mess invalide : " + args[i]);
            }
                                   
            else throw new ArgumentsException("Option invalide :"+ args[i]);
         }
      
      }
     
    
   	
   /** La méthode execute effectue un envoi de message par la source de la chaine de transmission du Simulateur. 
   * @return les options explicites de simulation.
   *
   * @throws Exception si un problème survient lors de l'exécution
   *
   */ 
      public void execute() throws Exception {      
         
         //  source.emettre(); 
      	     	      
      }
   
   	   	
   	
   /** La méthode qui calcule le taux d'erreur binaire en comparant les bits du message émis avec ceux du message reçu.
   *
   * @return  La valeur du Taux dErreur Binaire.
   */   	   
      public float  calculTauxErreurBinaire() {
      
      	// A compléter
      	
         return  0.0f;
      }
   
   
   
   
   /** La fonction main instancie un Simulateur à l'aide des arguments paramètres et affiche le résultat de l'exécution d'une transmission.
   *  @param args les différents arguments qui serviront à l'instanciation du Simulateur.
   */
      public static void main(String [] args) { 
      
         Simulateur simulateur = null;
      	
         try {
            simulateur = new Simulateur(args);
         }
            catch (Exception e) {
               System.out.println(e); 
               System.exit(-1);
            } 
      		
         try {
            simulateur.execute();
            float tauxErreurBinaire = simulateur.calculTauxErreurBinaire();
            String s = "java  Simulateur  ";
            for (int i = 0; i < args.length; i++) {
         		s += args[i] + "  ";
         	}
            System.out.println(s + "  =>   TEB : " + tauxErreurBinaire);
         }
            catch (Exception e) {
               System.out.println(e);
               e.printStackTrace();
               System.exit(-2);
            }              	
      }
   	
   }

