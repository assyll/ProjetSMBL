package jsonAndGS;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map.Entry;

import generatorTracesTest.Action;
import generatorTracesTest.Trace;

public class TracesToJson {

	public static boolean write(Trace trace, String path) {
		return writeToFile(traceToJson(trace), path);
	}
	
	public static boolean write(List<Trace> traces, String path) {
		String contenu = "";
		for (Trace trace: traces) {
			contenu += traceToJson(trace);
		}
		return writeToFile(contenu, path);
	}
	
	public static String traceToJson(Trace trace) {
		
		trace.addProprietes(); // A NE PAS OUBLIER !!!!!
		
		String json = "";
		for (Action action: trace.getActions()) {
			json += "{";
			for (Entry<String, Object> properties:
				action.getProprietes().entrySet()) {
				
				json += "\"" + properties.getKey() + "\":";
				json += "\"" + properties.getValue() + "\",";
			}
			json = json.substring(0, json.length() - 1);
			json +="}\n";
		}
		return json;
	}
	
	public static boolean writeToFile(String contenu, String path) {
		if (contenu.equals("")) {
			return false;
		}
		
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter writer = new FileWriter(file);
			writer.write(contenu);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
}
