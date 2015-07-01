package trace.impl;

import general.ActionProvider;
import general.FET;
import general.TraceActionParser;
import general.TraceElementEater;
import generalStructure.interfaces.IInit;

import java.io.FileNotFoundException;

import trace.interfaces.ITakeAction;

public class ActionProviderImpl extends ActionProvider implements IInit {

	private String path;
	
	public ActionProviderImpl(String path) {
		this.path = path;
	}
	
	@Override
	public void init() {
		parts().fet().init();
		parts().tee().init();
	}
	
	@Override
	protected FET make_fet() {
		return new FETImpl(path);
	}

	@Override
	protected TraceElementEater make_tee() {
		return new TraceElementEaterImpl();
	}

	@Override
	protected TraceActionParser make_traceParser() {
		return new TraceActionParserImpl();
	}

	@Override
	protected IInit make_init() {
		return this;
	}

}
