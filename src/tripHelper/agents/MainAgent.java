package tripHelper.agents;
import java.io.IOException;
import java.util.ArrayList;

import tripHelper.utils.Path;
import tripHelper.utils.PathAgentConfiguration;
import tripHelper.utils.Segment;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;






public class MainAgent extends Agent {

	public static final boolean DEBUG = true;
	private static final long serialVersionUID = -3194090991124107894L;
	
	private ArrayList<AID> pathAgents;
	
	public MainAgent () {
		
		// Creating list of path agents 
		pathAgents = new ArrayList<AID>();
		pathAgents.add(new AID("pa1", AID.ISLOCALNAME ));
		pathAgents.add(new AID("pa2", AID.ISLOCALNAME ));
		
	}

	protected void setup() {

		// Sending configuration to Path Agents
		this.addBehaviour(this.configureCalculationAgents());
		
		// Setting up recive messages 
		this.addBehaviour(this.reciveBehaviour());
		
		
	}
	
	
	
	
	
	// Behaviours
	

	private CyclicBehaviour reciveBehaviour() {

		return new CyclicBehaviour() {

			private static final long serialVersionUID = -6435156850120548042L;

			@Override
			public void action() {
				ACLMessage msg = receive();
				if (msg != null) {
					switch (msg.getPerformative()) {

					case ACLMessage.REQUEST:
						
						// TODO : tymaczosowe otrzymywanie requestu - tym zajmnie sie GUI
						String msgContent[] = msg.getContent().split(",");
						String from = "";
						String to = "";
						try {
							from = msgContent[0];
							to = msgContent[1];
						} catch (Exception e) {
							//TODO
						}
						
						if (DEBUG) {
							System.out
									.println(String
											.format("%s : Otrzymalem zadanie trasy ( z %s do %s ) komunikat od %s ",
													myAgent.getLocalName(),
													from,
													to,
													msg.getSender()));
						}
						
						// Wysylanie polcenia do agentow
						addBehaviour(createPathRequest(from,to));
						
					
					
					
						break;

					default:
						if (DEBUG) {
							System.out
									.println(String
											.format("%s : Otrzymalem nieznany komunikat od %s ",
													myAgent.getLocalName(),
													msg.getSender()));
						}
						break;

					}
				}
			}
		};
	}
	
	private OneShotBehaviour configureCalculationAgents() {
		
		return new OneShotBehaviour() {
			
			private static final long serialVersionUID = -6435156850120548042L;

			@Override
			public void action() {
				
				int precission = 1;
				
				// Creating message with configuration for each agent 
				for (AID agent : pathAgents) {
					
					// Configuration
					PathAgentConfiguration agentConfiguration = new PathAgentConfiguration(precission);
					ACLMessage configurationMsg = new ACLMessage(ACLMessage.INFORM);
					
					// Serialize configuration
					configurationMsg.addReceiver(agent);
					try {
						configurationMsg.setContentObject(agentConfiguration);
					} catch (IOException e) {

					}
					
					if (DEBUG) {
						System.out.println(String.format("%s : Wysylam preczje (%d) do %s ", myAgent.getLocalName(), precission , agent.getLocalName()  ));
					}
					
					// Precisson incrementation
					precission++;
					
					// Sending
					myAgent.send(configurationMsg);
				}
				
			}
		};
	}
	
	private OneShotBehaviour createPathRequest(final String _from, final String _to){
		
		
		
		return new OneShotBehaviour() {
			
			private static final long serialVersionUID = -6435155850120548042L;

			@Override
			public void action() {
				
				// Creating message with initial path for agents
				ACLMessage requestMsg = new ACLMessage(ACLMessage.REQUEST);
				
				// Creating path
				Path tmpPath = new Path();
				tmpPath.add(_from, _to, 0.0f, Segment.type.none);
				
				// Add agents 
				for (AID agent : pathAgents) {	
					requestMsg.addReceiver(agent);
				}
				
				// Send 
				send(requestMsg);
			}
			
			
		};
	}
	
	
	
	
	
	
}
