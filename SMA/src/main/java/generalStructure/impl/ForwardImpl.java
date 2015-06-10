package generalStructure.impl;

import environnement.interfaces.ContextInfos;
import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import general.Forward;
import generalStructure.interfaces.CycleAlert;
import generalStructure.interfaces.ICreateAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import trace.Action;
import trace.interfaces.ITakeAction;
import agents.interfaces.PullMessage;
import agents.interfaces.SendMessage;


public class ForwardImpl extends Forward<CycleAlert, ContextInfos,EnvInfos,  EnvUpdate, SendMessage, PullMessage, ITakeAction> implements SendMessage {

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
	protected StateForward<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction> make_StateForward(String id, boolean isRoot){
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

}
