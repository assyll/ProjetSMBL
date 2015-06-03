package trace.impl;

import general.TraceElementEater;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import trace.Action;
import trace.ActionTrace;
import trace.interfaces.IGetAction;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class TraceElementEaterImpl extends TraceElementEater implements Runnable, IGetAction{

	Queue<ActionTrace> actionList = new LinkedList<ActionTrace>();
	
	@Override
	public void run() {
		
		String traceElement = this.requires().traceElement().getNextElement();
		
		
		while(!traceElement.isEmpty())
		{
			try {
				ActionTrace action = getActionFromTraceElement(traceElement);
				if(action != null){
					actionList.add(action);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println(traceElement);
			traceElement = this.requires().traceElement().getNextElement();
		}
		
	}
	
	@Override
	protected void start(){
		super.start();
		this.run();
		
	}

	@Override
	protected IGetAction make_actionElement() {
		return this;
	}

	@Override
	public ActionTrace getNextAction() {
		return actionList.poll();
	}

	@Override
	public String getNextActionUser() {
		// TODO Auto-generated method stub
		return actionList.peek().getUserName();
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




}
