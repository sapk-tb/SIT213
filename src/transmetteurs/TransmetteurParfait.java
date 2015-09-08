package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

public class TransmetteurParfait extends Transmetteur<Boolean, Boolean> {

	@Override
	public void recevoir(Information<Boolean> information) throws InformationNonConforme {
		this.informationRecue=information;
		emettre();
	}

	@Override
	public void emettre() throws InformationNonConforme {
		// TODO Auto-generated method stub
		for (DestinationInterface <Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationRecue);
         }
		this.informationEmise=this.informationRecue;

	}

}
