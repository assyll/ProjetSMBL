package trace.impl;

import java.io.FileNotFoundException;

import trace.BigComponent;
import trace.FET;
import trace.TraceElementEater;

public class BigComponentImpl extends BigComponent {

	@Override
	protected FET make_fet() {
		FETImpl fet = null;
		try {
			fet = new FETImpl("C:\\Traces\\trace5.txt");
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		
		return fet; 
	}

	@Override
	protected TraceElementEater make_traceEater() {
		return new TraceElementEaterImpl();
	}
	

}
