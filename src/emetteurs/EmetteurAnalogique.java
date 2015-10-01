/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;
import java.util.LinkedList;

/**
 *
 * @author agirar01
 */
public class EmetteurAnalogique extends Emetteur<Boolean, Float> {
    private final String form;
    private final int nbEch;
    private final float amplMin;
    private final float amplMax;

    public EmetteurAnalogique(String form, int nbEch, float amplMin, float amplMax) {
        super();
        //TODO check validity of args
        this.form=form;
        this.nbEch=nbEch;
        this.amplMin=(form=="RZ")?0.0f:amplMin;
        this.amplMax=amplMax;
    }

    @Override
    public void recevoir(Information<Boolean> information) throws InformationNonConforme {
        this.informationRecue = information;
        emettre();
    }

    @Override
    public void emettre() throws InformationNonConforme {
        Information<Float> informationAEmettre = new Information<Float>();

        for (int i = 0; i < informationRecue.nbElements(); i++) {
            Boolean bit = informationRecue.iemeElement(i);
            for (int n = 0; n < nbEch; n++) {
                //TODO NRZT
                informationAEmettre.add((float) (bit ? amplMax : amplMin));
            }
        }

        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationAEmettre);
        }

        this.informationEmise = informationAEmettre;
    }

}
