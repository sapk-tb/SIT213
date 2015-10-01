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

    private final String form;
    private final int nbEch;
    private final float amplMin;
    private final float amplMax;

    public RecepteurAnalogique(String form, int nbEch, float amplMin, float amplMax) {
        super();
        //TODO check validity of args
        this.form = form;
        this.nbEch = nbEch;
        this.amplMin = amplMin;
        this.amplMax = amplMax;
    }

    @Override
    public void recevoir(Information<Float> information) throws InformationNonConforme {
        this.informationRecue = information;
        emettre();
    }

    @Override
    public void emettre() throws InformationNonConforme {
        Information<Boolean> informationAEmettre = new Information<Boolean>();
        float total[] = new float[informationRecue.nbElements()/nbEch];
        for (int i = 0; i < informationRecue.nbElements(); i++) {
            total[(int) i/nbEch] += informationRecue.iemeElement(i);
        }
        
        for (int i = 0; i < informationRecue.nbElements()/nbEch; i++) {
            float moy_symbole = total[i]/(float)nbEch;
            /* if (Math.abs(amplMax-moy_symbole) < Math.abs(amplMin-moy_symbole)) {
                informationAEmettre.add(true);
            } else {
                informationAEmettre.add(false);
            }*/
            informationAEmettre.add((Math.abs(amplMax - moy_symbole) < Math.abs(amplMin - moy_symbole)));
            
        }

        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationAEmettre);
        }

        this.informationEmise = informationAEmettre;
    }

}
