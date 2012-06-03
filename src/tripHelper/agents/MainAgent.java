package tripHelper.agents;
import java.io.IOException;
import java.util.ArrayList;
import tripHelper.utils.PathAgentConfiguration;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;






public class MainAgent extends Agent {

	
	private static final long serialVersionUID = -3194090991124107894L;
	private ArrayList<AID> calculationAgents;
	
	public static final boolean DEBUG = true;
	
	
	
	public MainAgent () {
		
		// Creating list of path agents 
		calculationAgents = new ArrayList<AID>();
		calculationAgents.add(new AID("pa1", AID.ISLOCALNAME ));
		calculationAgents.add(new AID("pa2", AID.ISLOCALNAME ));
		
	}

	protected void setup() {

		// Wysylanie konfiguracji do agentow obliczeniowych
		this.addBehaviour(this.configureCalculationAgents());
		
		
	}
	
	
	// Behaviours
	
	private OneShotBehaviour configureCalculationAgents() {
		
		return new OneShotBehaviour() {
			
			private static final long serialVersionUID = -6435156850120548042L;

			@Override
			public void action() {
				
				int precission = 1;
				
				// Creating message with configuration for each agent 
				for (AID agent : calculationAgents) {
					
					// Configuration
					PathAgentConfiguration agentConfiguration = new PathAgentConfiguration(precission);
					ACLMessage configurationMsg = new ACLMessage(ACLMessage.INFORM);
					
					// Serialize configuration
					configurationMsg.addReceiver(agent);
					try {
						configurationMsg.setContentObject(agentConfiguration);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					if (DEBUG) {
						System.out.println(String.format("%s : Wysylam precyje (%d) do %s ", myAgent.getLocalName(), precission , agent.getLocalName()  ));
					}
					
					// Precisson incrementation
					precission++;
					
					// Sending
					myAgent.send(configurationMsg);
				}
				
			}
		};
	}
	
	
	
	
	
	
	
	
}
