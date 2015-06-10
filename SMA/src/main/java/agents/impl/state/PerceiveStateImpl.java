package agents.impl.state;

import java.util.List;

import trace.Action;
import trace.ActionTrace;
import agents.impl.AbstractPerceive;
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
		System.out.println("Perception de " + id);
		
		//Je regarde si j'attend une prochaine transition d'un utilisateur
		if(requires().memory().isWaitingForTraceElmt()) {
			//J'essai de récupérer la prochaine trace via le forward et de la mettre dans la mémoire de l'agent
			List<String> userNameList = requires().memory().getUserNameWaitingForTraceList();
			int i = 0;
			ActionTrace action = null;
			
			while( (i < userNameList.size()) && (action == null) ) {
				action = requires().getContext().getActionTrace(userNameList.get(i));
				i++;
			}
			
			if(action != null) {
				i--;
				requires().memory().addAction(action);
			}
		}
		
		//Si je suis racine, je regarde si il y a de nouveaux noms d'utilisateurs non encore rencontrés jusqu'ici
		if(requires().memory().isRoot()) {
			List<ActionTrace> userNameList = requires().getContext().newUsersTraceList();
			
			for(int i = 0; i < userNameList.size(); i++) {
				ActionTrace action = userNameList.get(i);
				requires().memory().addAction(action);
			}
		}
		
		
	}

}
