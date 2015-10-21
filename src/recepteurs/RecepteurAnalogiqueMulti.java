package recepteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

/**
 * Classe d'un composant recepteur d'informations dont les élèments sont de type
 * RecepteurAnalogique qui hérite de la classe Recepteur
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 * @author Pierrick CHOVELON
 * @author Mélanie CORRE
 */
public class RecepteurAnalogiqueMulti extends RecepteurAnalogique {

    private final Integer[] dt;
    private final Float[] ar;

    public Integer[] getDt() {
        return dt;
    }

    public Float[] getAr() {
        return ar;
    }

    /**
     * Constructeur du récepteur analogique
     *
     * @param form Forme du signal à recevoir
     * @param nbEch Nombre d'écahntillon par symbole
     * @param amplMin Amplitude pour la valeur binaire 0
     * @param amplMax Amplitude pour la valeur binaire 1
     * @param dutyCycleRZ Dutycycle à utiliser dans le cadre d'une forme RZ
     * @param tmpMontee Temps de montée à respecté dans le cadre d'une forme
     * NRZT
     * @param dt Tableau de décalage des multitrajet
     * @param ar Tableau d'atténuation des multitrajet
     */
    public RecepteurAnalogiqueMulti(String form, int nbEch, float amplMin, float amplMax, float dutyCycleRZ, float tmpMontee, Integer[] dt, Float[] ar) {
        super(form, nbEch, amplMin, amplMax, dutyCycleRZ, tmpMontee);
        this.dt = dt;
        this.ar = ar;
    }

    /**
     * Enlève les trajets multiples des échantillons //TODO ajouter des solution
     * pour limiter les différents bruits
     *
     * @param infRecue L'information à nettoyer
     * @return L'information nettoyée
     * @throws InformationNonConforme
     */
    protected Information<Float> cleanEch(Information<Float> infRecue) throws InformationNonConforme {
        if (infRecue == null) {
            throw new InformationNonConforme("informationRecue == null");
        }
        /* Calcul du décalage maximum */
        int dtmax = 0;
        for (int i = 0; i < dt.length; i++) {
            if (ar[i] != 0 && dt[i] > dtmax) { // Si on est au dessus et que l'on a une amplitude non nulle
                dtmax = dt[i];
            }
        }
        int nbEchTotal = infRecue.nbElements();
        int nbEchFinal = nbEchTotal - (dtmax);
        int nbSymbole = nbEchTotal / nbEch;
        Information<Float> informationNettoyee = new Information(nbEchTotal);
        //TODO case dt[i] = 0;
        for (int i = 0; i < nbEchFinal; i++) {
            informationNettoyee.addAt(i, infRecue.iemeElement(i));

            for (int j = 0; j < dt.length; j++) {
                if (ar[j] != 0 && (i - dt[j]) >= 0) { // Si on a un décalage et que l'amplitude est non nulle
                    float valeurSignalPrec = informationNettoyee.iemeElement(i - dt[j]);
                    float valeurReflection = (float) valeurSignalPrec * ar[j];
                    informationNettoyee.setIemeElement(i, (float) informationNettoyee.iemeElement(i) - valeurReflection);
                }
            }
        }
        System.out.println("nbEch après nettoyage : " + informationNettoyee.nbElements());
        /*
         for (int i = 0; i < nbEchFinal; i++) {
         infoDecodee.addAt(i, informationRecue.iemeElement(i));
         for (int j = 0; j < dt.length; j++) {
         if (ar[j] == 0) {
         continue; //l'amplitude de la reflection est de 0 -> on passe
         }
         if ((i - dt[j]) > 0) { //TODO defini if not better >=
         float valeur = (float) infoDecodee.iemeElement(i - dt[j]) * ar[j];
         infoDecodee.setIemeElement(i, (float) infoDecodee.iemeElement(i) - valeur);
         }
         }
         }
         */
        return informationNettoyee;
    }

    /**
     * émet l'information construite par l'emetteur
     *
     * @throws information.InformationNonConforme
     */
    @Override
    public void emettre() throws InformationNonConforme {
        this.informationEmise = parseEch(cleanEch(this.informationRecue));
        envoyerAuxSuivants();
    }

}
