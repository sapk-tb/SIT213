/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package information;

/**
 * Information analogique pour ajouter des fonction spécifique à ces signaux.
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 * @author Pierrick CHOVELON
 * @author Mélanie CORRE
 */
public class InformationAnalogique extends Information<Float> {

    //TODO generate moyenne at add?
    public float getMoyenne() {
        float total = 0;
        for (Float content : this.content) {
            total += (float) content;
        }
        return total / this.content.size();
    }

}
