package tripHelper.agents;

import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;



public class MainAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3194090991124107894L;
	private ArrayList<AID> calculationAgents;
	
	
	public MainAgent () {
		
		// Creating list of path agents 
		calculationAgents = new ArrayList<AID>();
		calculationAgents.add(new AID("pa1", AID.ISLOCALNAME ));
		calculationAgents.add(new AID("pa2", AID.ISLOCALNAME ));
		
	}
	
	
	protected void setup() {

	}
	
	
	
	
	
	
	
	
}
