package recepteurs;

import information.Information;
import information.InformationNonConforme;

/**
 * Classe d'un composant recepteur d'informations dont les �l�ments sont de type
 * Double qui h�rite de la classe Recepteur
 *
 * @author Antoine GIRARD
 * @author C�dric HERZOG
 * @author Pierrick CHOVELON
 * @author M�lanie CORRE
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
     * Constructeur du r�cepteur analogique
     *
     * @param form Forme du signal � recevoir
     * @param nbEch Nombre d'�cahntillon par symbole
     * @param amplMin Amplitude pour la valeur binaire 0
     * @param amplMax Amplitude pour la valeur binaire 1
     * @param dutyCycleRZ Dutycycle � utiliser dans le cadre d'une forme RZ
     * @param tmpMontee Temps de mont�e � respecter dans le cadre d'une forme
     * NRZT
     * @param dt Tableau de d�calage des multitrajets
     * @param ar Tableau d'att�nuation des multitrajets
     */
    public RecepteurAnalogiqueMulti(String form, int nbEch, double amplMin, double amplMax, double dutyCycleRZ, double tmpMontee, Integer[] dt, Double[] ar) {
        super(form, nbEch, amplMin, amplMax, dutyCycleRZ, tmpMontee);
        this.dt = dt;
        this.ar = ar;
    }

    /**
     * Constructeur du r�cepteur analogique
     *
     * @param form Forme du signal � recevoir
     * @param nbEch Nombre d'�cahntillon par symbole
     * @param amplMin Amplitude pour la valeur binaire 0
     * @param amplMax Amplitude pour la valeur binaire 1
     * @param dutyCycleRZ Dutycycle � utiliser dans le cadre d'une forme RZ
     * @param tmpMontee Temps de mont�e � respecter dans le cadre d'une forme
     * NRZT
     * @param dt Tableau de d�calage des multitrajets
     * @param ar Tableau d'att�nuation des multitrajets
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
        /* Calcul du d�calage maximum */
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
        //System.out.println("nbEch apr�s stripping : " + informationStriped.nbElements());
        return informationStriped;
    }

    /**
     * Enl�ve les trajets multiples des �chantillons //TODO ajouter des solutions
     * pour limiter les diff�rents bruits
     *
     * @param infRecue L'information � nettoyer
     * @return L'information nettoy�e
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
                if (ar[j] != 0 && (i - dt[j]) >= 0) { // Si on a un d�calage et que l'amplitude est non nulle
                    double valeurSignalPrec = informationNettoyee.iemeElement(i - dt[j]);
                    double valeurReflection = valeurSignalPrec * ar[j];
                    informationNettoyee.setIemeElement(i, informationNettoyee.iemeElement(i) - valeurReflection);
                }
            }
        }
        return informationNettoyee;
    }

    /**
     * �met l'information construite par l'emetteur
     *
     * @throws information.InformationNonConforme
     */
    @Override
    public void emettre() throws InformationNonConforme {
        this.informationEmise = parseEch((noMultiCorrection) ? stripEch(this.informationRecue) : cleanEch(this.informationRecue));
        envoyerAuxSuivants();
    }

}
