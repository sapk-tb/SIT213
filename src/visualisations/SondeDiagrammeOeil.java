package visualisations;

import information.Information;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Classe réalisant l'affichage d'information composée d'élèments réels (double)
 *
 * @author prou
 */
public class SondeDiagrammeOeil extends Sonde<Double> {

    private final int nbEchParSym;
    private String filename = null;
    private Integer screenSize = null;

    /**
     * pour construire une sonde analogique
     *
     * @param nom le nom de la fenêtre d'affichage
     * @param nbEchParSym Le nombre d'echantillon par symbole
     * @param screenFilename Le nom du fichier ou enregistrer le screenshot
     * @param screenSize taille de la capture de l'oeil
     */
    public SondeDiagrammeOeil(String nom, int nbEchParSym, String screenFilename, Integer screenSize) {
        this(nom, nbEchParSym);
        this.filename = screenFilename;
        this.screenSize = screenSize;
    }

    /**
     * pour construire une sonde analogique
     *
     * @param nom le nom de la fenêtre d'affichage
     * @param nbEchParSym Le nombre d'echantillon par symbole
     * @param screen_filename Le nom du fichier ou enregistrer le screenshot
     */
    public SondeDiagrammeOeil(String nom, int nbEchParSym, String screen_filename) {
        this(nom, nbEchParSym);
        this.filename = screen_filename;
    }

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
    public void recevoir(Information<Double> information) {
        informationRecue = information;
        int nbElements = information.nbElements();
        int nbSym = nbElements / nbEchParSym;
        double[][] table = new double[nbSym][nbEchParSym];
        //System.out.println("Nb Sym = " + nbSym + " nbEchParSym : " + nbEchParSym + " nbElements " + nbElements);
        for (int i = 0; i < nbSym; i++) {
            for (int j = 0; j < nbEchParSym; j++) {
                table[i][j] = information.iemeElement(i * nbEchParSym + j);
            }
        }
        /*int i = 0;
         for (double f : information) {
         table[i / nbEchParSym][i % nbEchParSym] = f;
         i++;
         }*/
        VueCourbe vue = new VueCourbe(table, nom);
        if (screenSize != null) {
            vue.setSize(screenSize, screenSize);
        }
        if (filename != null) {
            BufferedImage img = tools.Render.getScreenShot(vue);
            try {
                ImageIO.write(img, "png", new File(filename)); //"screenshot-" + nom + ".png"
            } catch (IOException ex) {
                Logger.getLogger(SondeDiagrammeOeil.class.getName()).log(Level.SEVERE, null, ex);
            }
            vue.dispose();
            //vue.dispatchEvent(new WindowEvent(vue, WindowEvent.WINDOW_CLOSING));
        }

    }

}
