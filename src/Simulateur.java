
import sources.*;
import destinations.*;
import emetteurs.EmetteurAnalogique;
import recepteurs.Recepteur;
import recepteurs.RecepteurAnalogiqueMulti;
import recepteurs.RecepteurAnalogiqueMultiIntelligent;
import tools.Tool;
import transducteurs.TransducteurEmetteur;
import transducteurs.TransducteurRecepteur;
import transmetteurs.*;
import visualisations.SondeAnalogique;
import visualisations.SondeDiagrammeOeil;
import visualisations.SondeFFT;
import visualisations.SondeLogique;
import visualisations.SondePuissance;
import visualisations.SondeRepartitionAnalogique;

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
    private final Transmetteur<Boolean, Boolean> transmetteurLogique = null;
    /**
     * le composant Destination de la chaine de transmission
     */
    private Destination<Boolean> destination = null;
    /**
     * le composant Transmetteur parfait analogique de la chaine de transmission
     */
    private final Transmetteur transmetteurAnalogique;
    //private final TransmetteurAnalogiqueBruite transmetteurAnalogique;
    //private final Transmetteur transmetteurAnalogique;
    /**
     * le composant Recepteur parfait analogique de la chaine de transmission
     */
    private final Recepteur recepteur;
    /**
     * le composant Emetteur logique->analogique de la chaine de transmission
     */
    private final EmetteurAnalogique emetteur;

    /**
     * le composant Transducteur en émision de la chaine de transmission
     */
    private TransducteurEmetteur transducteurEmetteur;

    /**
     * le composant Transducteur en réception de la chaine de transmission
     */
    private TransducteurRecepteur transducteurRecepteur;

    /**
     * la forme du signal, par défaut il s'agit d'un signal RZ
     */
    private String form = "RZ";

    /**
     * l'amplitude minimale du signal, par défaut il s'agit de 0
     */
    private Double amplMin = 0.0;

    /**
     * l'amplitude maximale du signal, par défaut il s'agit de 1
     */
    private Double amplMax = 1.0;

    /**
     * le nombre d'échantillons du signal, par défaut il s'agit de 30
     */
    private Integer nbEch = 30;
    /**
     * le temps haut ou bas du signal RZ
     */
    private final double dutyCycleRZ = 1.0 / 3.0;
    /**
     * le temps de montée ou de descente à 1/3 du temps bit
     */
    private final double tmpMontee = 1.0 / 3.0;
    private Double snr = null; // en linéaires
    private Double snrdB;

    /* Information pour les multi-trajets */
    private Integer nbTrajet = 0;
    private Integer[] dt = {0, 0, 0, 0, 0};
    private Double[] ar = {0.0, 0.0, 0.0, 0.0, 0.0};

    private boolean generate_pictures = false;
    /* generate picture when l'agrument -sat-img est saisie */

    private String pictureFolder;
    /* définit le dossier ou mettre les images de -stat-img */

    private Integer pictureSize;
    /* définit la taille des visualisations exportées avec -stat-img */

    private boolean affichageFFT = false;
    /* Affiche un graphique de la FFT */

    private boolean affichageOeil = false;
    /* Affiche un graphique de l'oeil */

    private boolean affichageRepartition = false;
    /* Affiche un graphique de répartition */

    private boolean transducteur = false; //Active le transducteur

    private boolean noMultiCorrection = false; // déssactive la correction des multi-trajets
    private boolean quickMode = false; // Simplifie certains calcul (bruit gaussien)
    private boolean aveugle = false; // Mode aveugle pour le recepeteur
    private Integer nbSymParOeil = 2;

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

        if (messageAleatoire) {
            System.out.println("Mode aléatoire : " + nbBitsMess);
            if (aleatoireAvecGerme) {
                source = new SourceAleatoire(nbBitsMess, seed);
            } else {
                source = new SourceAleatoire(nbBitsMess);
            }
        } else {
            System.out.println("Mode  non aléatoire : " + messageString);
            source = new SourceFixe(messageString);
        }

        /*
         * Affichage des paramètres
         */
        System.out.println("Paramètre de transmission : " + form + " / " + nbEch + " / " + amplMin + " / " + amplMax);

        /*
         * instancie emetteur de type EmetteurAnalogique avec les paramètres
         * propres à la classe
         */
        emetteur = new EmetteurAnalogique(form, nbEch, amplMin, amplMax, dutyCycleRZ, tmpMontee);

        if (transducteur == true) {
            /*
             * On relie la source au transducteur et le transducteur à l'émetteur
             */
            transducteurEmetteur = new TransducteurEmetteur();

            source.connecter(transducteurEmetteur);
            transducteurEmetteur.connecter(emetteur);

        } else//fonctionnement normal
        {
            /*
             * On relie la source à l'emetteur
             */
            source.connecter(emetteur);
        }

        /*
         * instancie transmetteurAnalogique de type
         * TransmetteurAnalogiqueParfait
         */
        if (aleatoireAvecGerme) {
            transmetteurAnalogique = new TransmetteurAnalogiqueBruiteMulti(dt, ar, snr, seed, quickMode);
        } else {
            transmetteurAnalogique = new TransmetteurAnalogiqueBruiteMulti(dt, ar, snr, quickMode);
        }
        /*
         * On relie l'emetteur au transmetteurAnalogique
         */
        emetteur.connecter(transmetteurAnalogique);

        /*
         * instancie recepteur de type RecepteurAnalogique avec les paramètres
         * propres à la classe
         */
        if (aveugle) {
            recepteur = new RecepteurAnalogiqueMultiIntelligent(form, nbEch, dutyCycleRZ, tmpMontee); //TODO use a more simple type with less information
        } else {
            recepteur = new RecepteurAnalogiqueMulti(form, nbEch, amplMin, amplMax, dutyCycleRZ, tmpMontee, dt, ar, noMultiCorrection);
        }
        /*
         * On relie le transmetteurAnalogique au recepteur
         */
        transmetteurAnalogique.connecter(recepteur);
        /*
         * instancie destination de type DestinationFinale
         */
        destination = new DestinationFinale();

        if (transducteur == true) {

            /*
             * On relie le recepteur au transducteur en réception et le transducteur à la destination
             */
            transducteurRecepteur = new TransducteurRecepteur();
            recepteur.connecter(transducteurRecepteur);
            transducteurRecepteur.connecter(destination);
        } else {//fonctionnement normal
            /*
             * On relie le recepteur à la destination
             */
            recepteur.connecter(destination);
        }


        /*
         * Affichage des sondes
         */
        if (affichage) {
            source.connecter(new SondeLogique("sondeApresSource", 256));
            emetteur.connecter(new SondeAnalogique("sondeApresEmetteur"));
            emetteur.connecter(new SondePuissance("sondePuissanceApresEmetteur"));

            transmetteurAnalogique.connecter(new SondeAnalogique("sondeApresTransmetteur"));
            transmetteurAnalogique.connecter(new SondePuissance("sondePuissanceApresTransmetteur"));

            recepteur.connecter(new SondeLogique("sondeApresRecepteur", 256));
            if (transducteur) {
                transducteurEmetteur.connecter(new SondeLogique("sondeApresTransducteurEmetteur", 256));
                transducteurRecepteur.connecter(new SondeLogique("sondeApresTransducteurRecepteur", 256));
            }
        }

        if (affichageOeil) {
            emetteur.connecter(new SondeDiagrammeOeil("sondeDiagrammeOeilApresEmetteur", nbEch*nbSymParOeil));
            transmetteurAnalogique.connecter(new SondeDiagrammeOeil("sondeDiagrammeOeilApresTransmetteur", nbEch*nbSymParOeil));
        }
        if (affichageRepartition) {
            if (snr != null) {
                transmetteurAnalogique.connecter(new SondeRepartitionAnalogique("sondeRepartitionAprèsTransmetteur", Math.min(amplMin, amplMin * 1 / snr) - 1, Math.max(amplMax, amplMax * 1 / snr) + 1));
            } else {
                transmetteurAnalogique.connecter(new SondeRepartitionAnalogique("sondeRepartitionAprèsTransmetteur", amplMin - 1, amplMax + 1));
            }
        }
        if (affichageFFT) {
            emetteur.connecter(new SondeFFT("sondeFFTApresEmetteur"));
            transmetteurAnalogique.connecter(new SondeFFT("sondeFFTApresTransmetteur"));
        }
        if (generate_pictures) { //TODO use args to be able to choose folder../data/img/
            emetteur.connecter(new SondeDiagrammeOeil("sondeDiagrammeOeilApresEmetteur", nbEch, pictureFolder + "/sondeDiagrammeOeilApresEmetteur-" + form + "-" + nbBitsMess + "-" + nbEch + "-" + snrdB + ".png", pictureSize));
            transmetteurAnalogique.connecter(new SondeDiagrammeOeil("sondeDiagrammeOeilApresTransmetteur", nbEch, pictureFolder + "/sondeDiagrammeOeilApresTransmetteur-" + form + "-" + nbBitsMess + "-" + nbEch + "-" + snrdB + ".png", pictureSize));
        }
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
     * <dt> -form f </dt><dd> codage (String) RZ, NRZ, NRZT, la forme d'onde du
     * signal à transmettre (RZ par défaut)</dd>
     * <dt> -nbEch ne </dt><dd> ne (int) le nombre d'échantillons par bit (ne
     * &gt;= 6 pour du NRZ, ne &gt;= 9 pour du NRZT, ne &gt;= 18 pour du RZ, 30
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
    public final void analyseArguments(String[] args) throws ArgumentsException {

        for (int i = 0; i < args.length; i++) {

            if (args[i].matches("-s")) {
                affichage = true;
            } else if (args[i].matches("-fft")) {
                affichageFFT = true;
            } else if (args[i].matches("-quick")) {
                quickMode = true;
            } else if (args[i].matches("-aveugle")) {
                aveugle = true;
            } else if (args[i].matches("-repartition")) {
                affichageRepartition = true;
            } else if (args[i].matches("-noMultiCorrection")) {
                noMultiCorrection = true;
            } else if (args[i].matches("-doeil")) {
                affichageOeil = true;
            } else if (args[i].matches("-nbSymParOeil")) {
                i++; // on passe à l'argument suivant
                nbSymParOeil = new Integer(args[i]);
            }  else if (args[i].matches("-stat-img")) {
                generate_pictures = true;
                if (i + 1 >= args.length) {
                    throw new ArgumentsException("Valeur du parametre folder -stat-img non saisie !");
                }
                if (i + 2 >= args.length) {
                    throw new ArgumentsException("Valeur du parametre size -stat-img non saisie !");
                }

                i++;
                pictureFolder = args[i];
                i++; // on passe à l'argument suivant
                pictureSize = new Integer(args[i]);
            } else if (args[i].matches("-seed")) {
                aleatoireAvecGerme = true;
                i++;
                // traiter la valeur associee
                try {
                    seed = new Integer(args[i]);
                } catch (Exception e) {
                    throw new ArgumentsException("Valeur du parametre -seed  invalide :" + args[i]);
                }
            } else if (args[i].matches("-snr")) {
                i++;
                // traiter la valeur associee
                try {
                    snrdB = new Double(args[i]);
                    System.out.println("SNR en dB saisie : " + snrdB);
                    snr = Tool.dBToLin(snrdB);
                    System.out.println("SNR en Lin : " + snr);
                } catch (Exception e) {
                    throw new ArgumentsException("Valeur du parametre -snr  invalide :" + args[i]);
                }
            } else if (args[i].matches("-mess")) {
                i++;
                Integer NB_MAX_SYMBOLE = 6;
                if (System.getenv("SIMU_NB_MAX_SYMBOLE") != null) {
                    NB_MAX_SYMBOLE = new Integer(System.getenv("SIMU_NB_MAX_SYMBOLE"));
                }
                // traiter la valeur associee
                messageString = args[i];
                if (args[i].matches("[0,1]{" + (NB_MAX_SYMBOLE + 1) + ",}")) {
                    messageAleatoire = false;
                    nbBitsMess = args[i].length();
                } else if (args[i].matches("[0-9]{1," + NB_MAX_SYMBOLE + "}")) {
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
                if (!form.matches("RZ") && !form.matches("NRZ") && !form.matches("NRZT")) {
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
                amplMin = new Double(args[i]);
                i++; // on passe à l'argument suivant
                amplMax = new Double(args[i]);

                if (amplMax <= amplMin) {
                    throw new ArgumentsException("Valeurs du parametre -ampl invalide : min:" + amplMin + " > max:" + amplMax);
                }
            } else if (args[i].matches("-nbEch")) {
                if (i + 1 >= args.length) {
                    throw new ArgumentsException("Valeur du parametre nbEch de -nbEch non saisie !");
                }
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

            } else if (args[i].matches("-ti")) {
                //Verification de la saisie du paramètre i ar dt
                String[] params = {"-ti", "i", "dt", "ar"};
                for (int j = 1; j <= 3; j++) {
                    if (i + j >= args.length || args[i + j].startsWith("-")) {
                        throw new ArgumentsException("Valeur du parametre " + params[j] + " -ti non saisie !");
                    }
                }

                i++;
                //On récupère le numero de trajet
                Integer nTrajet = new Integer(args[i]);
                if (!(nTrajet >= 1 && nTrajet <= 5)) {
                    throw new ArgumentsException("Valeur du parametre numTrajet <1 ou >5");
                }

                i++;
                //On récupère le dt du trajet
                dt[nTrajet] = new Integer(args[i]);
                i++;
                //On récupère le ar du trajet
                ar[nTrajet] = new Double(args[i]);

                nbTrajet = 0;
                for (int j = 0; j < 5; j++) {
                    if (ar[j] != 0) {
                        nbTrajet++; //nbTrajet indique le nombre de trajet non null qui seront généré mais n'est pas indispensable
                    }
                }
                System.out.println("nbTrajet : " + nbTrajet);
            } else if (args[i].matches("-transducteur")) {
                transducteur = true;

            } else {
                throw new ArgumentsException("Option invalide : " + args[i]);
            }
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
    public double calculTauxErreurBinaire() {
        int nbSymbole = source.getInformationEmise().nbElements();

        Boolean Emits[] = new Boolean[nbSymbole];
        source.getInformationEmise().toArray(Emits);

        Boolean Recus[] = new Boolean[nbSymbole];
        destination.getInformationRecue().toArray(Recus);

        return Tool.compare(Recus, Emits);
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
            double tauxErreurBinaire = simulateur.calculTauxErreurBinaire();
            String s = "java  Simulateur  ";
            for (String arg : args) {
                s += arg + "  ";
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
