package jsonAndGS;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import generatorTracesTest.Action;
import generatorTracesTest.Trace;

public class TracesToJson {

	public static boolean write(Trace trace, String path) {
		return writeToFile(stringListToString(traceToJson(trace)), path);
	}
	
	public static boolean write(List<Trace> traces, String path) {
		List<String> actionsListJson = new ArrayList<String>();
		for (Trace trace: traces) {
			actionsListJson.addAll(traceToJson(trace));
		}
		return writeToFile(stringListToString(actionsListJson), path);
	}
	
	private static String stringListToString(List<String> actionsListJson) {
		Queue<String> actionsTriees = trierSelonTimeStamp(actionsListJson);
		String contenu = "";
		for (String actionString: actionsTriees) {
			contenu += actionString + "\n";
		}
		return contenu;
	}
	
	private static Queue<String> trierSelonTimeStamp(List<String> actionsJson) {
		PriorityQueue<String> actionsTriees = new PriorityQueue<String>(
				actionsJson.size(), new Comparator<String>() {
					
					@Override
					public int compare(String arg0, String arg1) {
						Date date1 = extraireTimeStamp(arg0);
						Date date2 = extraireTimeStamp(arg1);
						System.out.print(date1+" ? "+date2+":");
						if (date1.equals(date2)) {
							System.out.println("egales!!");
							return 0;
						} else if (date1.before(date2)) {
							System.out.println("INF");
							return -1;
						} else {
							System.out.println("SUP");
							return 1;
						}
					}
					
					private Date extraireTimeStamp(String actionJson) {
						JsonFactory jfactory = new JsonFactory();
						try {
							JsonParser jParser = jfactory.createParser(actionJson);
							jParser.nextToken();
							String jToken = "";
							
							while (!jToken.equals("timeStamp")) {
								jParser.nextToken();
								jToken = jParser.getText();
							}
							
							jParser.nextToken();
							jToken = jParser.getText();
							Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a").
									parse(jToken);
							
							return date;
							
						} catch (Exception e) {
							return null;
						}
					}
				});
		
		for (String actionJson: actionsJson) {
			actionsTriees.add(actionJson);
			if (actionsTriees.size() < 10) {
				System.out.println("----------------------");
				for (String string: new ArrayList<String>(actionsTriees)) {
					System.out.println(string);
				}
			}
		}
		
		return actionsTriees;
	}
	
	public static List<String> traceToJson(Trace trace) {
		
		trace.addProprietes(); // A NE PAS OUBLIER !!!!!
		
		List<String> actionsJson = new ArrayList<String>();
		
		for (Action action: trace.getActions()) {
			String json = "{";
			for (Entry<String, Object> properties:
				action.getProprietes().entrySet()) {
				
				json += "\"" + properties.getKey() + "\":";
				json += "\"" + properties.getValue() + "\",";
			}
			json = json.substring(0, json.length() - 1);
			json +="}";
			
			actionsJson.add(json);
		}
		
		return actionsJson;
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
