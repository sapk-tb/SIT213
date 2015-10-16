package recepteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

/**
 * Classe d'un composant recepteur d'informations dont les élèments sont de type
 * RecepteurAnalogique qui hérite de la classe Recepteur
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 * @author Pierrick CHOVELON
 * @author Mélanie CORRE
 */
public class RecepteurAnalogiqueMulti extends Recepteur<Float, Boolean> {

    private final String form;
    private final int nbEch;
    private final float amplMin;
    private final float amplMax;
    private final float dutyCycleRZ;
    private final float tmpMontee;
    private final Integer[] dt;
    private final Float[] ar;
    
    
    public String getForm() {
        return form;
    }

    public int getNbEch() {
        return nbEch;
    }

    public float getAmplMin() {
        return amplMin;
    }

    public float getAmplMax() {
        return amplMax;
    }

    public float getDutyCycleRZ() {
        return dutyCycleRZ;
    }

    public float getTmpMontee() {
        return tmpMontee;
    }
    
    public Integer[] getDt(){
    	return dt;
    }
    
    public Float[] getAr(){
    	return ar;
    }

    /**
     * Constructeur du récepteur analogique
     *
     * @param form Forme du signal à recevoir
     * @param nbEch Nombre d'écahntillon par symbole
     * @param amplMin Amplitude pour la valeur binaire 0
     * @param amplMax Amplitude pour la valeur binaire 1
     * @param dutyCycleRZ Dutycycle à utiliser dans le cadre d'une forme RZ
     * @param tmpMontee Temps de montée à respecté dans le cadre d'une forme
     * NRZT
     */
    public RecepteurAnalogiqueMulti(String form, int nbEch, float amplMin, float amplMax, float dutyCycleRZ, float tmpMontee, Integer[] dt, Float[] ar) {
        super();
        //TODO check validity of args
        this.form = form;
        this.nbEch = nbEch;
        this.amplMin = amplMin;
        this.amplMax = amplMax;
        this.dutyCycleRZ = dutyCycleRZ;
        this.tmpMontee = tmpMontee;
        this.dt=dt;
        this.ar=ar;
    }

    /**
     * reçoit une information. Cette méthode, en fin d'exécution, appelle la
     * méthode emettre.
     *
     * @param information l'information reçue
     */
    @Override
    public void recevoir(Information<Float> information) throws InformationNonConforme {
        this.informationRecue = information;
        emettre();
    }

    /**
     * émet l'information construite par l'emetteur
     */
    @Override
    public void emettre() throws InformationNonConforme {
        if (informationRecue == null) {
            throw new InformationNonConforme("informationRecue == null");
        }
        
        //Debut debruitage du signal
        int dtmax=0;
        for(int i=0;i<dt.length;i++){
        	if(dt[i]>dtmax){
        		dtmax=dt[i];
        	}
        }
        Information infoDecodee=new Information();
        for(int i=0; i<(informationRecue.nbElements()-dtmax);i++){
        	infoDecodee.addAt(i, informationRecue.iemeElement(i));
        	for(int j=0;j<dt.length;j++){
        		if((i-dt[j])>0){
        			float valeur=(float)infoDecodee.iemeElement(i-dt[j])*ar[j];
        			infoDecodee.addAt(i, -valeur);
        		}
        	}
        }
        //Fin debruitage du signal
        
        
        int nbEchTotal = informationRecue.nbElements();
        int nbSymbole = nbEchTotal / nbEch;
        Information<Boolean> informationAEmettre = new Information<Boolean>(nbSymbole);
        //Float allEch[] = new Float[nbEchTotal];
        float total[] = new float[nbSymbole];

//        informationRecue.toArray(allEch);
        /*
         * Calcul de la somme pour chaque échantillon
         */
        //for (int i = 0; i < nbEchTotal; i++) {
        for (int i = 0; i < nbSymbole; i++) {
            //total[(int) i / nbEch] += allEch[i];
            for (int n = 0; n < nbEch; n++) {
                total[i] += informationRecue.iemeElement(i * nbEch + n);
            }
        }

        /*
         * Calcul de la moyenne d'un symbole afin de retrouver le niveau de
         * chaque échantillon
         */
        for (int i = 0; i < nbEchTotal / nbEch; i++) {
            float moy_symbole = total[i] / (float) nbEch;
            //System.out.println("Moy symbole : "+moy_symbole);

            switch (form) {
                case "RZ":
                    informationAEmettre.add((Math.abs(amplMax * dutyCycleRZ - moy_symbole) < Math.abs(amplMin * dutyCycleRZ - moy_symbole)));
                    break;
                case "NRZ":
                    informationAEmettre.add((Math.abs(amplMax - moy_symbole) < Math.abs(amplMin - moy_symbole)));
                    break;
                case "NRZT":
                    informationAEmettre.add((Math.abs(amplMax * (1 - tmpMontee / 2) - moy_symbole) < Math.abs(amplMin * (1 - tmpMontee / 2) - moy_symbole)));
                    break;
            }
            //TODO check if we replace inforamtion à emetrre par un Float[]  for performance ?
        }

        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationAEmettre);
        }

        this.informationEmise = informationAEmettre;
    }

}
