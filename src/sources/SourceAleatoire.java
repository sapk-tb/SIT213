package sources;

import information.Information;

public class SourceAleatoire extends Source<Boolean> {
	public SourceAleatoire(int nbBits) {
	  	super();
		this.informationGeneree = new Information<Boolean>();
		for(int i=0; i<nbBits; i++){
			this.informationGeneree.add(Math.random()<0.5);
		}
  }
}
