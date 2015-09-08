package sources;

import information.Information;

public class SourceFixe extends Source<Boolean> {

	   
	      public SourceFixe(String messageSimulateur) throws Exception {
	    	  	super();
	    		this.informationGeneree = new Information<Boolean>();
				//TODO simmplify since it's already check by the args analyze
	    		for(int i=0; i<messageSimulateur.length(); i++){
	    			switch(messageSimulateur.charAt(i)) {
	    				case '1' :
	    					this.informationGeneree.add(true);
	    				break;
	    				case '0' : 
	    					this.informationGeneree.add(false);
	    				break;
	    				default:
	    					throw new Exception("Caractère invalide dans la chaine de caractère : "+messageSimulateur.charAt(i));
	    			}
	    			
	    		}
	      }
}
