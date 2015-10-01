/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

/**
 *
 * @author agirar01
 */
public class EmetteurAnalogique extends Emetteur<Boolean, Float> {

    private final String form;
    private final int nbEch;
    private float amplMin;
    private final float amplMax;
    private final float tmpMontee;
    private final float dutyCycleRZ;

    public EmetteurAnalogique(String form, int nbEch, float amplMin, float amplMax, float dutyCycleRZ, float tmpMontee) {
        super();
        //TODO check validity of args
        this.form = form;
        this.nbEch = nbEch;
        this.amplMin = amplMin;
        this.amplMax = amplMax;
        this.dutyCycleRZ = dutyCycleRZ;
        this.tmpMontee = tmpMontee;
    }

    @Override
    public void recevoir(Information<Boolean> information) throws InformationNonConforme {
        this.informationRecue = information;
        emettre();
    }

    @Override
    public void emettre() throws InformationNonConforme {
        Information<Float> informationAEmettre = new Information<Float>();

        float niveauPrecedent = 0f;
        int nbEchTransition = (int) (tmpMontee * nbEch);
        float deltaAmplitude = amplMax - amplMin;
        float coefDirecteur = deltaAmplitude / nbEchTransition; //TODO think if not to be ammply in the loop
        float deltaEntreEch = coefDirecteur * 1; // *1 echantillon

        //System.out.println("Debug : " + nbEchTransition + " / " + deltaAmplitude + " / " + coefDirecteur + " / " + deltaEntreEch);
        for (int i = 0; i < informationRecue.nbElements(); i++) {
            Boolean bit = informationRecue.iemeElement(i);
            for (int n = 0; n < nbEch; n++) {
                //TODO NRZT check for not to have delay ????
                switch (form) {
                    case "RZ":
                        if (n < nbEch * dutyCycleRZ) {
                            informationAEmettre.add((float) (bit ? amplMax : amplMin));
                        } else {
                            informationAEmettre.add(0f);
                        }
                        break;
                    case "NRZR":
                        informationAEmettre.add((float) (bit ? amplMax : amplMin));
                        break;
                    case "NRZT":
                        float niveauAnalogique = (float) (bit ? amplMax : amplMin);
                        //TODO simplify
                        //System.out.println("Debug loop : " + niveauPrecedent + " / " + niveauAnalogique + " / " + deltaEntreEch);

                        if (Math.abs(niveauPrecedent - niveauAnalogique) > deltaEntreEch) {
                            if (Math.max(niveauPrecedent, niveauAnalogique) == niveauAnalogique) {
                                niveauPrecedent = niveauPrecedent + deltaEntreEch;
                            } else {
                                niveauPrecedent = niveauPrecedent - deltaEntreEch;
                            }
                        } else {
                            niveauPrecedent = niveauAnalogique;
                        }
                        informationAEmettre.add(niveauPrecedent);

                        break;
                }
            }
        }

        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationAEmettre);
        }

        this.informationEmise = informationAEmettre;
    }

}
