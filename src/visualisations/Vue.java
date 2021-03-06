package visualisations;

/**
 * @author B. Prou
 * 
*/
import javax.swing.*;

public class Vue extends JFrame {

    private static final long serialVersionUID = 1917L;

    protected static int xPosition = 0;
    protected static int yPosition = 0;

    public static int getXPosition() {
        xPosition += 30;
        return xPosition - 30;
    }

    public static int getYPosition() {
        yPosition += 30;
        return yPosition - 30;
    }

    public Vue(String nom) {
        super(nom);
    }

}
