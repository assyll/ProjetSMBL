package trace.impl;


import general.TraceElementEater;

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

public class TraceElementEaterImpl extends TraceElementEater implements Runnable, ITakeAction{
	private Map<String, List<ActionTrace>> actionsByUserMap = new HashMap<>();
	private List<String> newUsersList = new LinkedList<String>();

	@Override
	public void run() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				ActionTrace action = (ActionTrace) TraceElementEaterImpl.this.requires().traceElement().getNextElement();

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
		}).start();

	}

	@Override
	protected void start(){
		super.start();
		this.run();
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




}
