package agents.impl;

import java.util.ArrayList;
import java.util.List;

import agents.Forward;
import agents.interfaces.Do;
import agents.interfaces.ICreateAgent;

public class ForwardImpl extends Forward<Do> implements Do, ICreateAgent{

	private List<AgentImpl> list = new ArrayList<AgentImpl>();
	
	@Override
	protected Do make_i() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void doIt() {
		for(AgentImpl agent: list)
			agent.doIt();
	}

	@Override
	public void createNewAgent() {
		
		
	}
	
	

	@Override
	protected Agent<Do> make_Agent(){
		AgentImpl a = new AgentImpl();
		list.add(a);
		return a;
	}

	private class AgentImpl extends Agent<Do> implements Do {

		@Override
		public void doIt() {
			this.requires().a().doIt();
		}
		
	}
}
