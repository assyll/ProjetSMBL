package generalStructure.impl;

import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import general.Forward;
import generalStructure.interfaces.CycleAlert;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import trace.Action;
import trace.ActionTrace;
import agents.impl.RequestMessage;
import agents.impl.ResponseMessage;
import agents.interfaces.PullMessage;
import agents.interfaces.SendMessage;

public class ForwardImpl extends Forward<CycleAlert, EnvInfos, EnvUpdate, SendMessage, PullMessage> implements SendMessage {

	private List<StateForwardImpl> list = new ArrayList<StateForwardImpl>();
	
	@Override
	protected StateForward<CycleAlert, EnvInfos, EnvUpdate, SendMessage, PullMessage> make_StateForward(String id){
		StateForwardImpl a = new StateForwardImpl(id);
		list.add(a);
		return a;
	}

	@Override
	protected SendMessage make_l() {
		return this;
	}

	@Override
	protected general.Forward.TransForward<CycleAlert, EnvInfos, EnvUpdate, SendMessage, PullMessage> make_TransForward(
			String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
