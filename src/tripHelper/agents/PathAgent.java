package tripHelper.agents;

import java.io.IOException;

import tripHelper.utils.PathAgentConfiguration;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.tools.introspector.gui.MyDialog;

public class PathAgent  extends Agent {

	private static final long serialVersionUID = -155045182237519220L;


	public static final boolean DEBUG = true;
	
	
	private PathAgentConfiguration config;
	
	
	
	protected void setup() {
		
		this.addBehaviour(this.reciveBehaviour());
		
		
		
	}
	
	
	
	// Behaviours
	
	public CyclicBehaviour reciveBehaviour() {
		
		return new CyclicBehaviour() {
			
			@Override
			public void action() {
				ACLMessage msg = receive();
				if (msg != null) {
					switch (msg.getPerformative()) {
					
					
					case ACLMessage.INFORM : 
						
						try {
							config = (PathAgentConfiguration) msg.getContentObject();
						} catch (UnreadableException e) {

						}
						
						if (DEBUG) {
							System.out.println(String.format("%s : Otrzymalem konfiguracje (precyzja %d) od %s ", myAgent.getLocalName(), config.precission, msg.getSender()  ));
						}
						
						break;
					default :
						System.out.println(String.format("%s : Otrzymalem nieznany komunikat od %s ", myAgent.getLocalName(), msg.getSender()  ));
						break;
					
					
					}
				}
			}
		};
	}
	
}
