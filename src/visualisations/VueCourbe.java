package visualisations;

/**
 * @author B. Prou
 * 
*/
import java.awt.*;
import java.awt.geom.*;

public class VueCourbe extends Vue {

    private static final long serialVersionUID = 1917L;

    private Point2D.Double[] coordonnees;
    private double yMax = 0;
    private double yMin = 0;
    private double transparence;
    private boolean isTransparance;

    public VueCourbe(boolean[] valeurs, int nbPixels, String nom) {

        super(nom);

        int xPosition = Vue.getXPosition();
        int yPosition = Vue.getYPosition();
        setLocation(xPosition, yPosition);

        this.coordonnees = new Point2D.Double[(2 * valeurs.length) + 1];
        yMax = 1;
        yMin = 0;

        coordonnees[0] = new Point2D.Double(0, 0);

        for (int i = 0, j = 0; i < valeurs.length; i++, j += 2) {
            if (valeurs[i]) {
                coordonnees[j + 1] = new Point2D.Double(i, 1);
                coordonnees[j + 2] = new Point2D.Double(i + 1, 1);
            } else {
                coordonnees[j + 1] = new Point2D.Double(i, 0);
                coordonnees[j + 2] = new Point2D.Double(i + 1, 0);
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

    public VueCourbe(double[] valeurs, int nbPixels, String nom) {
        this(valeurs, nom);
        int largeur = (valeurs.length * nbPixels) + 10;
        if (largeur > 1000) {
            largeur = 1000;
        }
        setSize(largeur, 200);
    }

    public VueCourbe(double[] valeurs, String nom) {

        super(nom);

        int xPosition = Vue.getXPosition();
        int yPosition = Vue.getYPosition();
        setLocation(xPosition, yPosition);

        this.coordonnees = new Point2D.Double[valeurs.length];
        yMax = 0;
        yMin = 0;

        for (int i = 0; i < valeurs.length; i++) {
            if (valeurs[i] > yMax) {
                yMax = valeurs[i];
            }
            if (valeurs[i] < yMin) {
                yMin = valeurs[i];
            }
            coordonnees[i] = new Point2D.Double(i, valeurs[i]);
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

    public VueCourbe(double[][] tab_valeurs, String nom) {
        this(tab_valeurs, 4f / (double) tab_valeurs.length, nom, true);
    }

    public VueCourbe(double[][] tab_valeurs, String nom, boolean toPaint) {
        this(tab_valeurs, 4f / (double) tab_valeurs.length, nom, toPaint);
    }

    public VueCourbe(double[][] tab_valeurs, double transparence, String nom, boolean toPaint) {

        super(nom);
        this.isTransparance = true;
        this.transparence = transparence;
        int xPosition = Vue.getXPosition();
        int yPosition = Vue.getYPosition();
        setLocation(xPosition, yPosition);
        int largeur_sym = tab_valeurs[0].length;
        yMax = 0;
        yMin = 0;

        this.coordonnees = new Point2D.Double[tab_valeurs.length * largeur_sym];
        for (int n = 0; n < tab_valeurs.length; n++) {
            double[] valeurs = tab_valeurs[n];

            for (int i = 0; i < valeurs.length; i++) {
                if (valeurs[i] > yMax) {
                    yMax = valeurs[i];
                }
                if (valeurs[i] < yMin) {
                    yMin = valeurs[i];
                }
                coordonnees[n * largeur_sym + i] = new Point2D.Double(i, valeurs[i]);
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

        this.coordonnees = new Point2D.Double[(2 * valeurs.length) + 1];
        yMax = 1;
        yMin = 0;

        coordonnees[0] = new Point2D.Double(0, 0);

        for (int i = 0, j = 0; i < valeurs.length; i++, j += 2) {
            if (valeurs[i]) {
                coordonnees[j + 1] = new Point2D.Double(i, 1);
                coordonnees[j + 2] = new Point2D.Double(i + 1, 1);
            } else {
                coordonnees[j + 1] = new Point2D.Double(i, 0);
                coordonnees[j + 2] = new Point2D.Double(i + 1, 0);
            }
        }

        paint();
    }

    public void changer(double[] valeurs) {

        this.coordonnees = new Point2D.Double[valeurs.length];
        yMax = 0;
        yMin = 0;

        for (int i = 0; i < valeurs.length; i++) {
            if (valeurs[i] > yMax) {
                yMax = valeurs[i];
            }
            if (valeurs[i] < yMin) {
                yMin = valeurs[i];
            }
            coordonnees[i] = new Point2D.Double(i, valeurs[i]);
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
        g.setColor(Color.black);

        int x0Axe = 15;
        double deltaX = getWidth() - (2 * x0Axe);

        int y0Axe = 15;
        double deltaY = getHeight() - (2 * y0Axe);

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
        double dx = deltaX / (double) coordonnees[coordonnees.length - 1].getX();
        double dy = 0.0f;
        if ((yMax >= 0) && (yMin <= 0)) {
            dy = deltaY / (yMax - yMin);
        } else if (yMin > 0) {
            dy = deltaY / yMax;
        } else if (yMax < 0) {
            dy = -(deltaY / yMin);
        }
        dy = (double) 0.8 * dy;
        int recursion = 0;

        if (isTransparance) {
            g.setColor(new Color(0, 0, 0, (float) Math.min(1, Math.max(0.002f, transparence * getHeight() / 128))));
            //g.setColor(new Color(0, 0, 0,  transparence * getHeight() / 128));
        }
        for (int i = 1; i < coordonnees.length; i++) {

            int x1 = (int) (coordonnees[i - 1].getX() * dx);
            int x2 = (int) (coordonnees[i].getX() * dx);
            int y1 = (int) (coordonnees[i - 1].getY() * dy);
            int y2 = (int) (coordonnees[i].getY() * dy);
            if (x1 > x2) { //We don't go back
                        /* //Test display a traject in color
                 if (recursion < 4) {
                 switch (recursion) {
                 case 0:
                 g.setColor(Color.BLUE);
                 break;
                 case 1:
                 g.setColor(Color.RED);
                 break;
                 case 2:
                 g.setColor(Color.GREEN);
                 break;
                 default:
                 g.setColor(new Color(0, 0, 0, transparence));
                 break;
                 }
                 recursion++;
                 }
                 */
                continue;
            }
            //System.out.print("1:{x:" + x1 + ",y:" + y1 + "}");
            //System.out.println("2:{x:" + x2 + ",y:" + y2 + "}");
            g.drawLine(x0Axe + x1, y0Axe - y1, x0Axe + x2, y0Axe - y2);
        }
        //*/
    }

}
