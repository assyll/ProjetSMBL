package testTraceParsing;

import java.io.FileNotFoundException;

import trace.impl.FETImpl;
import trace.impl.TraceActionParserImpl;
import general.FET;
import general.TraceActionParser;
import general.TraceActionParserTestComp;

public class TestActionParserTestCompImpl extends TraceActionParserTestComp{

	public String path;
	
	public TestActionParserTestCompImpl(String path) {
		this.path = path;
	}
	
	@Override
	protected FET make_fet() {
		return new FETImpl(path);
	}

	@Override
	protected TraceActionParser make_tap() {
		return new TraceActionParserImpl();
	}

}
