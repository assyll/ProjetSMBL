package generalStructure.impl;

import environnement.interfaces.ContextInfos;
import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import general.Forward;
import generalStructure.interfaces.CycleAlert;
import generalStructure.interfaces.ICreateAgent;
import generalStructure.interfaces.IStop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scala.util.control.Exception;
import trace.Action;
import trace.interfaces.ITakeAction;
import agents.impl.RequestMessage;
import agents.impl.ResponseMessage;
import agents.interfaces.PullMessage;
import agents.interfaces.SendMessage;


public class ForwardImpl extends Forward<CycleAlert, ContextInfos,EnvInfos,  EnvUpdate, SendMessage, PullMessage, ITakeAction> implements SendMessage, IStop {

	private int nbState;
	private int nbTrans;
	private Map<String,StateForwardImpl> stateFwList;
	private Map<String,TransForwardImpl> transFwList;
	private String rootId;

	public ForwardImpl() {
		stateFwList = new HashMap<String, StateForwardImpl>();
		transFwList = new HashMap<String, TransForwardImpl>();
		nbState = 0;
		nbTrans = 0;
		rootId = "";
	}
	
	@Override
	protected StateForward <CycleAlert, ContextInfos, EnvInfos, EnvUpdate,
	SendMessage, PullMessage, ITakeAction>
	make_StateForward(String id, boolean isRoot) {
		
		StateForwardImpl a = new StateForwardImpl(id);
		stateFwList.put(id, a);
		
		if(isRoot) {
			rootId = id;
		}
		
		return a;
	}

	@Override
	protected SendMessage make_l() {
		return this;
	}

	@Override
	protected TransForward<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage,ITakeAction> make_TransForward(
			String id) {
		TransForwardImpl a = new TransForwardImpl(id);
		transFwList.put(id, a);
		return a;
	}

	@Override
	public void notifyStop() {
		requires().log().closeFile();
	}

	@Override
	protected IStop make_stopProcessus() {
		return this;
	}

	@Override
	public void sendRequestMessage(RequestMessage request) {		
		// Recupere l'agent destinataire
		String reveiverId = request.getReceiverId();
		
		// Recupere son forward
		StateForwardImpl stateForward = stateFwList.get(reveiverId);
		TransForwardImpl transForward = transFwList.get(reveiverId);
		
		// place le message dans la bonne <<boite aux lettres>>
		if (stateForward != null) {
			stateForward.pushRequestMessage(request);
		} else if (transForward != null) {
			transForward.pushRequestMessage(request);
		} else {
			// ERREUR -> le forward du destinataire nexiste pas !
		}
		
	}

	@Override
	public void sendResponseMessage(ResponseMessage response) {
		// Recupere l'agent destinataire
		String reveiverId = response.getReceiverId();
		
		// Recupere son forward
		StateForwardImpl stateForward = stateFwList.get(reveiverId);
		TransForwardImpl transForward = transFwList.get(reveiverId);
		
		// place le message dans la bonne <<boite aux lettres>>
		if (stateForward != null) {
			stateForward.pushResponseMessage(response);
		} else if (transForward != null) {
			transForward.pushResponseMessage(response);
		} else {
			// ERREUR -> le forward du destinataire nexiste pas !
		}
	}

}
