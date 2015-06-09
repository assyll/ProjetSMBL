package trace.impl;


import general.TraceElementEater;

import java.io.IOException;
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

public class TraceElementEaterImpl extends TraceElementEater<ITakeAction> implements Runnable, ITakeAction{
	private Map<String, List<Action>> actionsByUserMap = new HashMap<>();

	@Override
	public void run() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				ActionTrace action = (ActionTrace) TraceElementEaterImpl.this.requires().traceElement().getNextElement();

				while(action != null) {
					if (actionsByUserMap.get(action.getUserName()) == null) {
						List<Action> queue = new LinkedList<>();
						queue.add(action.getAction());
						actionsByUserMap.put(action.getUserName(), queue);
					} else {
						actionsByUserMap.get(action.getUserName()).add(action.getAction());
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


	public ActionTrace getActionFromTraceElement(String traceElement) throws IOException
	{
		JsonParser parser = (new JsonFactory()).createParser(traceElement);
		JsonToken token = null;
		Map<String,String> actionFields = new HashMap<String,String>();
		String userName = "";
		ActionTrace actionTrace = null;
		Action action = new Action(); 

		while (!parser.isClosed()) {
			token = parser.nextToken();

			if(token==null){
				break;
			}

			if(JsonToken.FIELD_NAME.equals(token)){
				String tagName = parser.getCurrentName();
				token = parser.nextToken();
				String tagContent = parser.getText();

				if("userName".equals(tagName)){
					userName = tagContent;
				} else if("action".equals(tagName)){
					actionFields.put(tagName,tagContent);
				}
			}
		}

		if(!actionFields.isEmpty()){
			action.setActionMap(actionFields);
			actionTrace = new ActionTrace(userName, action);
		}

		return actionTrace;

	}

	@Override
	protected ITakeAction make_actionGetter() {
		return this;
	}

	@Override
	public Action getActionTrace(String userName) {
		if(!actionsByUserMap.containsKey(userName) || (actionsByUserMap.get(userName).isEmpty()))
			return null;
		else{
			Action action = actionsByUserMap.get(userName).get(0);
			actionsByUserMap.get(userName).remove(0);
			return  action;
		}
	}




}
