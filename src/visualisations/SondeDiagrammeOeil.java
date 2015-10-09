package visualisations;

import information.Information;

/**
 * Classe réalisant l'affichage d'information composée d'élèments réels (float)
 *
 * @author prou
 */
public class SondeDiagrammeOeil extends Sonde<Float> {

    private final int nbEchParSym;

    /**
     * pour construire une sonde analogique
     *
     * @param nom le nom de la fenêtre d'affichage
     * @param nbEchParSym Le nombre d'echantillon par symbole
     */
    public SondeDiagrammeOeil(String nom, int nbEchParSym) {
        super(nom);
        this.nbEchParSym = nbEchParSym;
    }

    @Override
    public void recevoir(Information<Float> information) {
        informationRecue = information;
        int nbElements = information.nbElements();
        int nbSym = nbElements / nbEchParSym;
        float[][] table = new float[nbSym][nbEchParSym];
        System.out.println("Nb Sym = " + nbSym + " nbEchParSym : " + nbEchParSym + " nbElements " + nbElements);
        for (int i = 0; i < nbSym; i++) {
            for (int j = 0; j < nbEchParSym; j++) {
                table[i][j] = information.iemeElement(i * nbEchParSym + j);
            }
        }
        /*int i = 0;
         for (float f : information) {
         table[i / nbEchParSym][i % nbEchParSym] = f;
         i++;
         }*/
        new VueCourbe(table, nom);
    }

}
