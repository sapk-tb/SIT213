package recepteurs;

import information.Information;
import information.InformationNonConforme;

/**
 * Classe d'un composant recepteur d'informations dont les elements sont de type
 * Double qui herite de la classe Recepteur
 *
 * @author Antoine GIRARD
 * @author Cedric HERZOG
 * @author Pierrick CHOVELON
 * @author Melanie CORRE
 */
public class RecepteurAnalogiqueMulti extends RecepteurAnalogique {

    protected Integer[] dt;
    protected Double[] ar;
    protected boolean noMultiCorrection = false;

    public Integer[] getDt() {
        return dt;
    }

    public Double[] getAr() {
        return ar;
    }

    /**
     * Constructeur du recepteur analogique
     *
     * @param form Forme du signal a recevoir
     * @param nbEch Nombre d'echantillons par symbole
     * @param amplMin Amplitude pour la valeur binaire 0
     * @param amplMax Amplitude pour la valeur binaire 1
     * @param dutyCycleRZ Dutycycle a utiliser dans le cadre d'une forme RZ
     * @param tmpMontee Temps de montee a respecter dans le cadre d'une forme
     * NRZT
     * @param dt Tableau de decalage des multitrajets
     * @param ar Tableau d'attenuation des multitrajets
     */
    public RecepteurAnalogiqueMulti(String form, int nbEch, double amplMin, double amplMax, double dutyCycleRZ, double tmpMontee, Integer[] dt, Double[] ar) {
        super(form, nbEch, amplMin, amplMax, dutyCycleRZ, tmpMontee);
        this.dt = dt;
        this.ar = ar;
    }

    /**
     * Constructeur du recepteur analogique
     *
     * @param form Forme du signal a recevoir
     * @param nbEch Nombre d'echantillons par symbole
     * @param amplMin Amplitude pour la valeur binaire 0
     * @param amplMax Amplitude pour la valeur binaire 1
     * @param dutyCycleRZ Dutycycle a utiliser dans le cadre d'une forme RZ
     * @param tmpMontee Temps de montee a respecter dans le cadre d'une forme
     * NRZT
     * @param dt Tableau de decalage des multitrajets
     * @param ar Tableau d'attenuation des multitrajets
     * @param noMultiCorrection
     */
    public RecepteurAnalogiqueMulti(String form, int nbEch, double amplMin, double amplMax, double dutyCycleRZ, double tmpMontee, Integer[] dt, Double[] ar, boolean noMultiCorrection) {
        this(form, nbEch, amplMin, amplMax, dutyCycleRZ, tmpMontee, dt, ar);
        this.noMultiCorrection = noMultiCorrection;
    }

    /**
     * Enleve les echantillons en trop
     *
     * @param infRecue
     * @return l'information raccourcie
     * @throws InformationNonConforme
     */
    protected Information<Double> stripEch(Information<Double> infRecue) throws InformationNonConforme {

        if (infRecue == null) {
            throw new InformationNonConforme("informationRecue == null");
        }
        /* Calcul du decalage maximum */
        int dtmax = 0;
        for (int i = 0; i < dt.length; i++) {
            if (ar[i] != 0 && dt[i] > dtmax) { // Si on est au dessus et que l'on a une amplitude non nulle
                dtmax = dt[i];
            }
        }
        int nbEchTotal = infRecue.nbElements();
        int nbEchFinal = nbEchTotal - (dtmax);

        Information<Double> informationStriped = new Information(nbEchFinal);
        for (int i = 0; i < nbEchFinal; i++) {
            informationStriped.add(infRecue.iemeElement(i));
        }
        //System.out.println("nbEch apres stripping : " + informationStriped.nbElements());
        return informationStriped;
    }

    /**
     * Enleve les trajets multiples des echantillons //TODO ajouter des solutions
     * pour limiter les differents bruits
     *
     * @param infRecue L'information a nettoyer
     * @return L'information nettoyee
     * @throws InformationNonConforme
     */
    protected Information<Double> cleanEch(Information<Double> infRecue) throws InformationNonConforme {
        if (infRecue == null) {
            throw new InformationNonConforme("informationRecue == null");
        }
        int nbEchTotal = infRecue.nbElements();

        Information<Double> informationNettoyee = stripEch(infRecue);
        int nbEchFinal = informationNettoyee.nbElements();
        //TODO case dt[i] = 0;
        for (int i = 0; i < nbEchFinal; i++) {

            for (int j = 0; j < dt.length; j++) {
                if (ar[j] != 0 && (i - dt[j]) >= 0) { // Si on a un decalage et que l'amplitude est non nulle
                    double valeurSignalPrec = informationNettoyee.iemeElement(i - dt[j]);
                    double valeurReflection = valeurSignalPrec * ar[j];
                    informationNettoyee.setIemeElement(i, informationNettoyee.iemeElement(i) - valeurReflection);
                }
            }
        }
        return informationNettoyee;
    }

    /**
     * emet l'information construite par l'emetteur
     *
     * @throws information.InformationNonConforme
     */
    @Override
    public void emettre() throws InformationNonConforme {
        this.informationEmise = parseEch((noMultiCorrection) ? stripEch(this.informationRecue) : cleanEch(this.informationRecue));
        envoyerAuxSuivants();
    }

}
