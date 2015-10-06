/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import information.Information;

/**
 * Classe d'outils static
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 * @author Pierrick CHOVELON
 * @author Mélanie CORRE
 */
public class Tool {

    public static float getMoyenne(Information<Float> inf) {
        float total = 0;
        for (Float content : inf) {
            total += (float) content;
        }
        return total / inf.nbElements();
    }
}
