package trace.impl;

import general.ActionProvider;
import general.FET;
import general.TraceActionParser;
import general.TraceElementEater;

import java.io.FileNotFoundException;

import trace.interfaces.ITakeAction;

public class ActionProviderImpl extends ActionProvider<ITakeAction> {

	private String path;
	
	public ActionProviderImpl(String path) {
		this.path = path;
	}
	
	@Override
	protected FET make_fet() {
		try {
			return new FETImpl(path);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	@Override
	protected TraceElementEater<ITakeAction> make_tee() {
		return new TraceElementEaterImpl();
	}

	@Override
	protected TraceActionParser make_traceParser() {
		return new TraceActionParserImpl();
	}

}
