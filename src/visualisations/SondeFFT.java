package visualisations;

import information.Information;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import tools.FFTBase;

/**
 * Classe réalisant l'affichage d'information composée d'élèments réels (double)
 *
 * @author prou
 */
public class SondeFFT extends Sonde<Double> {

    private String filename = null;
    private Integer screenSize = null;

    /**
     * pour construire une sonde analogique
     *
     * @param nom le nom de la fenêtre d'affichage
     * @param screenFilename Le nom du fichier ou enregistrer le screenshot
     * @param screenSize taille de la capture de l'oeil
     */
    public SondeFFT(String nom, String screenFilename, Integer screenSize) {
        this(nom);
        this.filename = screenFilename;
        this.screenSize = screenSize;
    }

    /**
     * pour construire une sonde analogique
     *
     * @param nom le nom de la fenêtre d'affichage
     * @param screen_filename Le nom du fichier ou enregistrer le screenshot
     */
    public SondeFFT(String nom, String screen_filename) {
        this(nom);
        this.filename = screen_filename;
    }

    /**
     * pour construire une sonde analogique
     *
     * @param nom le nom de la fenêtre d'affichage
     */
    public SondeFFT(String nom) {
        super(nom);
    }

    @Override
    public void recevoir(Information<Double> information) {
        informationRecue = information;
        int nbElementsOri = information.nbElements();

        //On détermine la puissance de 2 maximum à nbElement.
        int nbElements = 1;
        while (nbElements < nbElementsOri) {
            nbElements *= 2;
            //System.out.println("nbElement : " + nbElements);
        }
        //nbElements = nbElements / 2;
        //System.out.println("nbElement : " + nbElements + "nbElementOri : " + nbElementsOri); 0-Padding
        double[] real = new double[nbElements];
        double[] complex = new double[nbElements];
        //informationRecue.toArray(real);
        for (int i = 0; i < nbElementsOri; i++) {
            real[i] = informationRecue.iemeElement(i);
        }

        double[] fft = FFTBase.fft(real, complex, true);
//*
        int milieu = fft.length / 2;
        double[] fftCorrected = new double[fft.length];
        for (int i = 0; i < fft.length; i++) {
            if (i >= milieu) {
                fftCorrected[i] = Math.abs(fft[i - milieu]);
            } else {
                fftCorrected[i] = Math.abs(fft[i + milieu]);
            }
        }
//*/
        //TODO lisser la courbe en faisant une moyenne;
        VueCourbe vue = new VueCourbe(fftCorrected, nom);
        if (screenSize != null) {
            vue.setSize(screenSize, screenSize);
        }
        if (filename != null) {
            BufferedImage img = tools.Render.getScreenShot(vue);
            try {
                ImageIO.write(img, "png", new File(filename)); //"screenshot-" + nom + ".png"
            } catch (IOException ex) {
                Logger.getLogger(SondeFFT.class.getName()).log(Level.SEVERE, null, ex);
            }
            vue.dispose();
            //vue.dispatchEvent(new WindowEvent(vue, WindowEvent.WINDOW_CLOSING));
        }

    }

}
