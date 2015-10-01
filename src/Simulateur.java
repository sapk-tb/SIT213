
import sources.*;
import destinations.*;
import emetteurs.EmetteurAnalogique;
import recepteurs.RecepteurAnalogique;
import transmetteurs.*;
import visualisations.SondeAnalogique;
import visualisations.SondeLogique;

/**
 * La classe Simulateur permet de construire et simuler une chaine de
 * transmission composée d'une Source, d'un nombre variable de Transmetteur(s)
 * et d'une Destination.
 *
 * @author cousin
 * @author prou
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 * @author Pierrick CHOVELON
 * @author Mélanie CORRE
 */
public class Simulateur {

    /**
     * indique si le Simulateur utilise des sondes d'affichage
     */
    private boolean affichage = false;
    /**
     * indique si le Simulateur utilise un message généré de manière aléatoire
     */
    private boolean messageAleatoire = true;
    /**
     * indique si le Simulateur utilise un germe pour initialiser les
     * générateurs aléatoires
     */
    private boolean aleatoireAvecGerme = false;
    /**
     * la valeur de la semence utilisée pour les générateurs aléatoires
     */
    private Integer seed = null;
    /**
     * la longueur du message aléatoire à transmettre si un message n'est pas
     * impose
     */
    private int nbBitsMess = 100;
    /**
     * la chaine de caractères correspondant à m dans l'argument -mess m
     */
    private String messageString = "100";

    /**
     * le composant Source de la chaine de transmission
     */
    private Source<Boolean> source = null;
    /**
     * le composant Transmetteur parfait logique de la chaine de transmission
     */
    private Transmetteur<Boolean, Boolean> transmetteurLogique = null;
    /**
     * le composant Destination de la chaine de transmission
     */
    private Destination<Boolean> destination = null;
    /**
     * le composant Transmetteur parfait analogique de la chaine de transmission
     */
    private final TransmetteurAnalogiqueParfait transmetteurAnalogique;
    /**
     * le composant Recepteur parfait analogique de la chaine de transmission
     */
    private final RecepteurAnalogique recepteur;
    /**
     * le composant Emetteur logique->analogique de la chaine de transmission
     */
    private final EmetteurAnalogique emetteur;

    /**
     * la forme du signal, par défaut il s'agit d'un signal RZ
     */
    private String form = "RZ";

    /**
     * l'amplitude minimale du signal, par défaut il s'agit de 0
     */
    private Float amplMin = 0.0f;

    /**
     * l'amplitude maximale du signal, par défaut il s'agit de 1
     */
    private Float amplMax = 1.0f;

    /**
     * le nombre d'échantillons du signal, par défaut il s'agit de 30
     */
    private Integer nbEch = 30;
    /**
     * le temps haut ou bas du signal RZ
     */
    private final float dutyCycleRZ = (float) 1 / (float) 2;
    /**
     * le temps de montée ou de descente à 1/3 du temps bit
     */
    private final float tmpMontee = (float) 1 / (float) 3;

    /**
     * <p>
     * Le constructeur de Simulateur construit une chaine de transmission
     * composée d'une Source Boolean, d'une Destination Boolean et de
     * Transmetteur(s) [voir la méthode analyseArguments]...</p>
     * <p>
     * Les différents composants de la chaine de transmission (Source,
     * Transmetteur(s), Destination, Sonde(s) de visualisation) sont créés et
     * connectés.</p>
     *
     * @param args le tableau des différents arguments.
     *
     * @throws ArgumentsException si un des arguments est incorrect
     *
     */
    public Simulateur(String[] args) throws ArgumentsException, Exception {

        // analyser et récupérer les arguments
        analyseArguments(args);
        //*
        //TODO instanscier chaine de transmission et execter

        if (messageAleatoire) {
            System.out.println("Mode aléatoire : " + nbBitsMess);
            if (aleatoireAvecGerme) {
                source = new SourceAleatoire(nbBitsMess, seed);
            } else {
                source = new SourceAleatoire(nbBitsMess);
            }
            System.out.println("Mode aléatoire fini");
        } else {
            System.out.println("Mode  non aléatoire : " + messageString);
            source = new SourceFixe(messageString);
            System.out.println("Mode non aléatoire fini");
        }

        /**
         * Affichage des paramètres
         */
        System.out.println("Paramètre de transmission : " + form + " / " + nbEch + " / " + amplMin + " / " + amplMax);

        /**
         * instancie emetteur de type EmetteurAnalogique avec les paramètres
         * propres à la classe
         */
        emetteur = new EmetteurAnalogique(form, nbEch, amplMin, amplMax, dutyCycleRZ, tmpMontee);
        //emetteur = new EmetteurAnalogique("NRZR", 100, -1.0f, 1.0f);
        //emetteur = new EmetteurAnalogique("NRZT", 100, -1.0f, 1.0f);

        /**
         * On relie la source à l'emetteur
         */
        source.connecter(emetteur);

        /**
         * instancie transmetteurAnalogique de type
         * TransmetteurAnalogiqueParfait
         */
        transmetteurAnalogique = new TransmetteurAnalogiqueParfait();
        /**
         * On relie l'emetteur au transmetteurAnalogique
         */
        emetteur.connecter(transmetteurAnalogique);

        /**
         * instancie recepteur de type RecepteurAnalogique avec les paramètres
         * propres à la classe
         */
        recepteur = new RecepteurAnalogique(form, nbEch, amplMin, amplMax, dutyCycleRZ, tmpMontee);
        /**
         * On relie le transmetteurAnalogique au recepteur
         */
        transmetteurAnalogique.connecter(recepteur);
        /**
         * instancie destination de type DestinationFinale
         */
        destination = new DestinationFinale();
        /**
         * On relie le recepteur à la destination
         */
        recepteur.connecter(destination);

        /**
         * Affichage des sondes
         */
        if (affichage) {
            source.connecter(new SondeLogique("sondeApresSource", 256));
            emetteur.connecter(new SondeAnalogique("sondeApresEmetteur"));
            transmetteurAnalogique.connecter(new SondeAnalogique("sondeApresTransmetteur"));
            recepteur.connecter(new SondeLogique("sondeApresRecepteur", 256));
        }

        /* Ancienne chaîne :
         transmetteurLogique = new TransmetteurBooleanParfait();
         source.connecter(transmetteurLogique);
         destination = new DestinationFinale();
         transmetteurLogique.connecter(destination);
         if (affichage) {
         source.connecter(new SondeLogique("sondeApresEmission", 256));
         transmetteurLogique.connecter(new SondeLogique("sondeApresTransmission", 256));
         }
         */
    }

    /**
     * La méthode analyseArguments extrait d'un tableau de chaines de caractéres
     * les différentes options de la simulation. Elle met à jour les attributs
     * du Simulateur.
     *
     * @param args le tableau des différents arguments.
     *
     * <p>
     * Les arguments autorisés sont :</p>
     *
     * <dl>
     * <dt> -mess m  </dt><dd> m (String) constitué de 7 ou plus digits à 0 | 1,
     * le message à transmettre</dd>
     * <dt> -mess m  </dt><dd> m (int) constitué de 1 à 6 digits, le nombre de
     * bits du message "aléatoire" à transmettre</dd>
     * <dt> -s </dt><dd> utilisation des sondes d'affichage</dd>
     * <dt> -seed v </dt><dd> v (int) d'initialisation pour les générateurs
     * aléatoires</dd>
     *
     * <dt> -form f </dt><dd> codage (String) RZ, NRZR, NRZT, la forme d'onde du
     * signal à transmettre (RZ par défaut)</dd>
     * <dt> -nbEch ne </dt><dd> ne (int) le nombre d'échantillons par bit (ne
     * &gt;= 6 pour du NRZR, ne &gt;= 9 pour du NRZT, ne &gt;= 18 pour du RZ, 30
     * par défaut))</dd>
     * <dt> -ampl min max </dt><dd> min (float) et max (float), les amplitudes
     * min et max du signal analogique à transmettre ( min &lt; max, 0.0 et 1.0
     * par défaut))</dd>
     *
     * <dt> -snr s</dt>
     * <dd> s (float) le rapport signal/bruit en dB</dd>
     *
     * <dt> -ti i dt ar </dt><dd> i (int) numero du trajet indirect (de 1 à 5),
     * dt (int) valeur du decalage temporel du ième trajet indirect en nombre
     * d'échantillons par bit, ar (float) amplitude relative au signal initial
     * du signal ayant effectué le ième trajet indirect</dd>
     *
     * <dt> -transducteur </dt><dd> utilisation de transducteur</dd>
     *
     * <dt> -aveugle </dt><dd> les récepteurs ne connaissent ni l'amplitude min
     * et max du signal, ni les différents trajets indirects (s'il y en a).</dd>
     *
     * </dl>
     * <b>Contraintes</b> : Il y a des interdépendances sur les paramètres
     * effectifs.
     *
     * @throws ArgumentsException si un des arguments est incorrect.
     *
     */
    public void analyseArguments(String[] args) throws ArgumentsException {

        for (int i = 0; i < args.length; i++) {

            if (args[i].matches("-s")) {
                affichage = true;
            } else if (args[i].matches("-seed")) {
                aleatoireAvecGerme = true;
                i++;
                // traiter la valeur associee
                try {
                    seed = new Integer(args[i]);
                } catch (Exception e) {
                    throw new ArgumentsException("Valeur du parametre -seed  invalide :" + args[i]);
                }
            } else if (args[i].matches("-mess")) {
                i++;
                // traiter la valeur associee
                messageString = args[i];
                if (args[i].matches("[0,1]{7,}")) {
                    messageAleatoire = false;
                    nbBitsMess = args[i].length();
                } else if (args[i].matches("[0-9]{1,6}")) {
                    messageAleatoire = true;
                    nbBitsMess = new Integer(args[i]);
                    if (nbBitsMess < 1) {
                        throw new ArgumentsException("Valeur du parametre -mess invalide : " + nbBitsMess);
                    }
                } else {
                    throw new ArgumentsException("Valeur du parametre -mess invalide : " + args[i]);
                }

            } else if (args[i].matches("-form")) {
                if (i + 1 >= args.length || args[i + 1].startsWith("-")) {
                    throw new ArgumentsException("Valeur du parametre -form non saisie !");
                }
                i++;
                form = args[i];
                if (!form.matches("RZ") && !form.matches("NRZR") && !form.matches("NRZT")) {
                    throw new ArgumentsException("Valeur du parametre -form invalide : " + args[i]);
                }

            } else if (args[i].matches("-ampl")) {
                if (i + 1 >= args.length) {
                    throw new ArgumentsException("Valeur du parametre min -ampl non saisie !");
                }
                if (i + 2 >= args.length) {
                    throw new ArgumentsException("Valeur du parametre max -ampl non saisie !");
                }

                i++;
                amplMin = new Float(args[i]);
                i++; // on passe à l'argument suivant
                amplMax = new Float(args[i]);

                if (amplMax <= amplMin) {
                    throw new ArgumentsException("Valeurs du parametre -ampl invalide : min:" + amplMin + " > max:" + amplMax);
                }
            } else if (args[i].matches("-nbEch")) {
                i++;
                // traiter la valeur associee
                try {
                    nbEch = new Integer(args[i]);
                } catch (Exception e) {
                    throw new ArgumentsException("Valeur du parametre -nbEch  invalide :" + args[i]);
                }

                if (nbEch <= 0) {
                    throw new ArgumentsException("Valeur du parametre -nbEch  invalide (<=0):" + args[i]);
                }

            } else {
                throw new ArgumentsException("Option invalide :" + args[i]);
            }
        }

        switch (form) {
            case "NRZR":
                if (nbEch < 6) {
                    throw new ArgumentsException("Valeur du parametre -nbEch  invalide < 6 pour form NRZR:" + nbEch);
                }
                break;
            case "NRZT":
                if (nbEch < 9) {
                    throw new ArgumentsException("Valeur du parametre -nbEch  invalide < 9 pour form NRZT:" + nbEch);
                }
                break;
            case "RZ":
                if (nbEch < 18) {
                    throw new ArgumentsException("Valeur du parametre -nbEch  invalide < 18 pour form RZ:" + nbEch);
                }
                break;

        }

    }

    /**
     * La méthode execute effectue un envoi de message par la source de la
     * chaine de transmission du Simulateur.
     *
     * @throws Exception si un problème survient lors de l'exécution
     *
     */
    public void execute() throws Exception {
        source.emettre();
    }

    /**
     * La méthode qui calcule le taux d'erreur binaire en comparant les bits du
     * message émis avec ceux du message reçu.
     *
     * @return La valeur du Taux dErreur Binaire.
     */
    public float calculTauxErreurBinaire() {
        //source.getInformationEmise().iterator()
        //TODO make clean and light version
        int errors = 0;
        for (int i = 0; i < source.getInformationEmise().nbElements(); i++) {
            if (source.getInformationEmise().iemeElement(i) != destination.getInformationRecue().iemeElement(i)) {
                errors++;
            }
        }

        return (float) errors / (float) source.getInformationEmise().nbElements();
    }

    /**
     * La fonction main instancie un Simulateur à l'aide des arguments
     * paramètres et affiche le résultat de l'exécution d'une transmission.
     *
     * @param args les différents arguments qui serviront à l'instanciation du
     * Simulateur.
     */
    public static void main(String[] args) {

        Simulateur simulateur = null;

        try {
            simulateur = new Simulateur(args);
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
        }

        try {
            simulateur.execute();
            //*
            float tauxErreurBinaire = simulateur.calculTauxErreurBinaire();
            String s = "java  Simulateur  ";
            for (int i = 0; i < args.length; i++) {
                s += args[i] + "  ";
            }
            System.out.println(s + "  =>   TEB : " + tauxErreurBinaire);
            //*/
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(-2);
        }
    }
}
