package transmetteurs;

import tools.Tool;
import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;
import tools.ArrayTool;

/**
 * Classe d'un composant qui transmet des informations de type Float sans
 * défaut.
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 */
public class TransmetteurAnalogiqueParfaitMulti extends Transmetteur<Float, Float> {

	
    private final Integer nbTrajet;
    //Décalage en échantillions
    private final Integer[] dt;
    //Amplitude relative
    private final Float[] ar;

    public TransmetteurAnalogiqueParfaitMulti(Integer nbTrajet, Integer[] dt, Float[] ar) {
        super();
        this.nbTrajet = nbTrajet;
        this.dt=dt;
        this.ar=ar;
    }
    
    
    /**
     * reçoit une information. Cette méthode, en fin d'exécution, appelle la
     * méthode emettre.
     *
     * @param information l'information reçue
     * @throws information.InformationNonConforme  Quand l'information est invalide
     */
    @Override
    public void recevoir(Information<Float> information) throws InformationNonConforme {
        if(information == null){
            throw new InformationNonConforme("information recue == null");
        }
        this.informationRecue = information;
        emettre();
    }

    /**
     * émet l'information construite par la transmetteur
     */
    @Override
    public void emettre() throws InformationNonConforme {
    	int max=0;
    	for(int i=0 ; i<this.dt.length;i++){
    		if (dt[i]>max)max=dt[i];
    	}
        Information<Float> informationTotale=new Information<Float>(this.informationRecue.nbElements()+nbTrajet*max);
        informationTotale=informationRecue;
        
        Float [] tmp = new Float[informationRecue.nbElements()];
        informationRecue.toArray(tmp);
        Information<Float> temp = new Information<Float>(tmp);
        for(int i=0;i<nbTrajet; i++){
        	for(int j=0;j<dt[nbTrajet];j++){
        		temp.addAt(0,0f);
        		ArrayTool.sumArrays(informationTotale, temp);
        	}
        }
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationTotale);
        }
    	
        this.informationEmise = informationTotale;

    }

}
