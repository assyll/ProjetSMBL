package agents.impl.state;

import java.util.List;

import trace.ActionTrace;
import agents.impl.AbstractPerceive;
import agents.impl.Message;
import agents.impl.RequestMessage;
import agents.impl.ResponseMessage;
import agents.interfaces.PullMessage;
import agents.interfaces.StateMemory;
import environnement.interfaces.ContextInfos;

public class PerceiveStateImpl extends AbstractPerceive<ContextInfos, StateMemory, PullMessage> {

	private String id;

	public PerceiveStateImpl(String id) {
		this.id = id;
	}

	@Override
	public void makePerception() {
		System.out.println(id + " Perception");

		// J'actualise ma liste des voisins
		requires().memory().setAgentIdInMyCell(
				requires().getContext().getAllAgentsInCell(
						requires().memory().getActionList()));
		
		boolean hasRealizedAction = false;
		ActionTrace action = null;
		Message message;
		
		if(requires().memory().isWaitingForTraceElmt()) {
			System.out.println(id + ": attend un prochain element de trace");
			//J'essai de recuperer la prochaine trace via le forward et de la mettre dans la memoire de l'agent
			List<String> userNameList = requires().memory().getUserNameWaitingForTraceList();
			int i = 0;

			while( (i < userNameList.size()) && (action == null) ) {
				action = requires().getContext().getActionTrace(userNameList.get(i));
				i++;
			}

			if(action != null) {
				System.out.println(id + ": a recu un element de trace (ACTION = "+action.getAction()+")");
				i--;
				requires().memory().addAction(action);
			}
			
			hasRealizedAction = (action != null);
		}

		//Sinon si je suis racine, je regarde si il y a de nouveaux noms d'utilisateurs non encore rencontr�s jusqu'ici
		if (!hasRealizedAction && (requires().memory().isRoot()) && (action == null)) {
			System.out.println(id + ": est la racine et veut recuperer des nouveau users");

			List<ActionTrace> userNameList = requires().getContext().newUsersTraceList();

			for(int i = 0; i < userNameList.size(); i++) {
				System.out.println(id + ": est la racine et a recuperer un nouveau user: " + userNameList.get(i).getUserName()+" avec l'action "+
						userNameList.get(i).getAction().getActionMap().get("action"));
				action = userNameList.get(i);
				
				requires().memory().addAction(action);
			}
			hasRealizedAction = true;
		}
		
		// J'essaie de recuperer une reponse
		if (!hasRealizedAction && (message = requires().getMessage().pullResponseMessage()) != null) {
			requires().memory().setResponseMessage((ResponseMessage) message);
			hasRealizedAction = true;
		}
		// J'essaie de recuperer des requete
		if(!hasRealizedAction && (message = requires().getMessage().pullRequestMessage()) != null) {
			requires().memory().setRequestMessage((RequestMessage) message);
			System.out.println(id + ": recuperation requete de " +
					((RequestMessage) message).getSenderId());
			hasRealizedAction = true;
		}
	}

}
