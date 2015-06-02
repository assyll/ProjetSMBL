package trace.impl;

import general.BigComponent;
import general.FET;
import general.TraceElementEater;

import java.io.FileNotFoundException;


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
