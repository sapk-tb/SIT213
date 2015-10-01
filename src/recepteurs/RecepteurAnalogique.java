/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recepteurs;


import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

/**
 *
 * @author agirar01
 */
public class RecepteurAnalogique extends Recepteur<Float, Boolean> {

    @Override
    public void recevoir(Information<Float> information) throws InformationNonConforme {
        this.informationRecue = information;
        emettre();
    }

    @Override
    public void emettre() throws InformationNonConforme {



    }

}
