package destinations;

import information.Information;
import information.InformationNonConforme;

public class DestinationFinale extends Destination<Boolean>{

	@Override
	public void recevoir(Information<Boolean> information) throws InformationNonConforme {
		// TODO Auto-generated method stub
		this.informationRecue=information;
		
	}
	
}