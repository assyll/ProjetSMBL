package trace.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import general.TraceActionParser;
import trace.Action;
import trace.ActionTrace;
import trace.interfaces.TraceElement;

public class TraceActionParserImpl extends TraceActionParser implements TraceElement{

	@Override
	public ActionTrace getNextElement()  {
		String traceElement = (String) this.requires().traceElement().getNextElement();

		if(traceElement != "") {

			try {
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
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;

	}

	@Override
	protected TraceElement make_actionTrace() {
		return this;
	}

}
