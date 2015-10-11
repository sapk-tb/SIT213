package visualisations;

/**
 * @author B. Prou
 * 
*/
import java.awt.*;
import java.awt.geom.*;

public class VueCourbe extends Vue {

    private static final long serialVersionUID = 1917L;

    private Point2D.Float[] coordonnees;
    private float yMax = 0;
    private float yMin = 0;
    private boolean transparance = false;

    public VueCourbe(boolean[] valeurs, int nbPixels, String nom) {

        super(nom);

        int xPosition = Vue.getXPosition();
        int yPosition = Vue.getYPosition();
        setLocation(xPosition, yPosition);

        this.coordonnees = new Point2D.Float[(2 * valeurs.length) + 1];
        yMax = 1;
        yMin = 0;

        coordonnees[0] = new Point2D.Float(0, 0);

        for (int i = 0, j = 0; i < valeurs.length; i++, j += 2) {
            if (valeurs[i]) {
                coordonnees[j + 1] = new Point2D.Float(i, 1);
                coordonnees[j + 2] = new Point2D.Float(i + 1, 1);
            } else {
                coordonnees[j + 1] = new Point2D.Float(i, 0);
                coordonnees[j + 2] = new Point2D.Float(i + 1, 0);
            }
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        int largeur = (valeurs.length * nbPixels) + 10;
        if (largeur > 1000) {
            largeur = 1000;
        }
        setSize(largeur, 200);
        setVisible(true);
        paint();
    }

    public VueCourbe(float[] valeurs, int nbPixels, String nom) {
        this(valeurs, nom);
        int largeur = (valeurs.length * nbPixels) + 10;
        if (largeur > 1000) {
            largeur = 1000;
        }
        setSize(largeur, 200);
    }

    public VueCourbe(float[] valeurs, String nom) {

        super(nom);

        int xPosition = Vue.getXPosition();
        int yPosition = Vue.getYPosition();
        setLocation(xPosition, yPosition);

        this.coordonnees = new Point2D.Float[valeurs.length];
        yMax = 0;
        yMin = 0;

        for (int i = 0; i < valeurs.length; i++) {
            if (valeurs[i] > yMax) {
                yMax = valeurs[i];
            }
            if (valeurs[i] < yMin) {
                yMin = valeurs[i];
            }
            coordonnees[i] = new Point2D.Float(i, valeurs[i]);
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        int largeur = valeurs.length + 10;
        if (largeur > 1000) {
            largeur = 1000;
        }
        setSize(largeur, 200);
        setVisible(true);
        paint();
    }

    public VueCourbe(float[][] tab_valeurs, String nom) {
        this(tab_valeurs, nom, true);
    }

    public VueCourbe(float[][] tab_valeurs, String nom, boolean toPaint) {

        super(nom);
        this.transparance = true;
        int xPosition = Vue.getXPosition();
        int yPosition = Vue.getYPosition();
        setLocation(xPosition, yPosition);
        int largeur_sym = tab_valeurs[0].length;
        yMax = 0;
        yMin = 0;

        this.coordonnees = new Point2D.Float[tab_valeurs.length * largeur_sym];
        for (int n = 0; n < tab_valeurs.length; n++) {
            float[] valeurs = tab_valeurs[n];

            for (int i = 0; i < valeurs.length; i++) {
                if (valeurs[i] > yMax) {
                    yMax = valeurs[i];
                }
                if (valeurs[i] < yMin) {
                    yMin = valeurs[i];
                }
                coordonnees[n * largeur_sym + i] = new Point2D.Float(i, valeurs[i]);
            }
        }
        //System.out.println("Nb Sym = " + tab_valeurs.length + " nbEchParSym : " + largeur_sym + " nbElements " + coordonnees.length);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        int largeur = largeur_sym + 10;
        if (largeur > 1000) {
            largeur = 1000;
        } else if (largeur < 180) {
            largeur = 180;
        }
        setSize(largeur, 200);
        setVisible(true);
        if (toPaint) {
            paint(getGraphics());
        }
    }

    public void changer(boolean[] valeurs) {

        this.coordonnees = new Point2D.Float[(2 * valeurs.length) + 1];
        yMax = 1;
        yMin = 0;

        coordonnees[0] = new Point2D.Float(0, 0);

        for (int i = 0, j = 0; i < valeurs.length; i++, j += 2) {
            if (valeurs[i]) {
                coordonnees[j + 1] = new Point2D.Float(i, 1);
                coordonnees[j + 2] = new Point2D.Float(i + 1, 1);
            } else {
                coordonnees[j + 1] = new Point2D.Float(i, 0);
                coordonnees[j + 2] = new Point2D.Float(i + 1, 0);
            }
        }

        paint();
    }

    public void changer(float[] valeurs) {

        this.coordonnees = new Point2D.Float[valeurs.length];
        yMax = 0;
        yMin = 0;

        for (int i = 0; i < valeurs.length; i++) {
            if (valeurs[i] > yMax) {
                yMax = valeurs[i];
            }
            if (valeurs[i] < yMin) {
                yMin = valeurs[i];
            }
            coordonnees[i] = new Point2D.Float(i, valeurs[i]);
        }

        paint();
    }

    /**
     */
    public void paint() {
        paint(getGraphics());
    }

    public void paint(Graphics g) {
        if (g == null) {
            return;
        }
        // effacement total
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (transparance) {
            g.setColor(new Color(0, 0, 0, 0.05f));
        } else {
            g.setColor(Color.black);
        }

        int x0Axe = 10;
        float deltaX = getWidth() - (2 * x0Axe);

        int y0Axe = 30;
        float deltaY = getHeight() - (2 * y0Axe);

        if ((yMax > 0) && (yMin <= 0)) {
            y0Axe += (int) (deltaY * (yMax / (yMax - yMin)));
        } else if ((yMax > 0) && (yMin > 0)) {
            y0Axe += deltaY;
        } else if (yMax <= 0) {
            y0Axe += 0;
        }
        g.drawLine(x0Axe, y0Axe, x0Axe + (int) deltaX + x0Axe, y0Axe);
        g.drawLine(x0Axe + (int) deltaX + x0Axe - 5, y0Axe - 5, x0Axe + (int) deltaX + x0Axe, y0Axe);
        g.drawLine(x0Axe + (int) deltaX + x0Axe - 5, y0Axe + 5, x0Axe + (int) deltaX + x0Axe, y0Axe);

        g.drawLine(x0Axe, y0Axe, x0Axe, y0Axe - (int) deltaY - y0Axe);
        g.drawLine(x0Axe + 5, 5, x0Axe, 0);
        g.drawLine(x0Axe - 5, 5, x0Axe, 0);

        // tracer la courbe
        float dx = deltaX / (float) coordonnees[coordonnees.length - 1].getX();
        float dy = 0.0f;
        if ((yMax >= 0) && (yMin <= 0)) {
            dy = deltaY / (yMax - yMin);
        } else if (yMin > 0) {
            dy = deltaY / yMax;
        } else if (yMax < 0) {
            dy = -(deltaY / yMin);
        }
        for (int i = 1; i < coordonnees.length; i++) {

            int x1 = (int) (coordonnees[i - 1].getX() * dx);
            int x2 = (int) (coordonnees[i].getX() * dx);
            int y1 = (int) (coordonnees[i - 1].getY() * dy);
            int y2 = (int) (coordonnees[i].getY() * dy);
            if (x1 > x2) { //We don't go back
                continue;
            }
            //System.out.print("1:{x:" + x1 + ",y:" + y1 + "}");
            //System.out.println("2:{x:" + x2 + ",y:" + y2 + "}");
            g.drawLine(x0Axe + x1, y0Axe - y1, x0Axe + x2, y0Axe - y2);
        }
        //*/
    }

}
