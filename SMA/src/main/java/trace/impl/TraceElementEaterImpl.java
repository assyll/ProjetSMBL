package trace.impl;


import general.TraceElementEater;
import generalStructure.interfaces.IInit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import trace.Action;
import trace.ActionTrace;
import trace.interfaces.ITakeAction;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class TraceElementEaterImpl extends TraceElementEater
implements ITakeAction, IInit {
	
	private Thread _thread;
	private Map<String, List<ActionTrace>> actionsByUserMap = new HashMap<>();
	private List<String> newUsersList = new LinkedList<String>();

	@Override
	public void init() {
		actionsByUserMap = new HashMap<>();
		newUsersList = new LinkedList<String>();
				
		if (_thread != null) {
			_thread.interrupt();
			_thread = null;
		}
				
		_thread = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("trace run !!!!!!!!!!!!!!!!");

				ActionTrace action = null;
				while (action == null) {
					action = (ActionTrace) TraceElementEaterImpl.this.requires().traceElement().getNextElement();
				}

				while(action != null) {
					if (!actionsByUserMap.containsKey(action.getUserName())) {
						List<ActionTrace> queue = new LinkedList<>();
						queue.add(action);
						actionsByUserMap.put(action.getUserName(), queue);
						newUsersList.add(action.getUserName());
					} else {
						actionsByUserMap.get(action.getUserName()).add(action);
					}

					action = (ActionTrace) TraceElementEaterImpl.this.requires().traceElement().getNextElement();
				}
			}
		});
		
		_thread.start();
		
	}

	@Override
	protected void start(){
		super.start();
		init();
	}

	@Override
	protected ITakeAction make_actionGetter() {
		return this;
	}

	@Override
	public ActionTrace getActionTrace(String userName) {
		if(!actionsByUserMap.containsKey(userName) || (actionsByUserMap.get(userName).isEmpty()))
			return null;
		else{
			ActionTrace action = actionsByUserMap.get(userName).get(0);
			actionsByUserMap.get(userName).remove(0);
			return  action;
		}
	}

	@Override
	public List<ActionTrace> newUsersTraceList() {
		List<ActionTrace> actionTraceList = new ArrayList<ActionTrace>();
		
		for(int i = 0; i < newUsersList.size(); i++) {
			String user = newUsersList.get(i);
			actionTraceList.add(actionsByUserMap.get(user).get(0));
			actionsByUserMap.get(user).remove(0);
		}
		
		newUsersList.clear();
		return actionTraceList;
	}

	@Override
	protected IInit make_init() {
		return this;
	}

}
