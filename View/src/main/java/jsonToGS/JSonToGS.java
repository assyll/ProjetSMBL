package jsonToGS;
import java.io.File;
import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;

public class JSonToGS {
	final static String FILE_PATH = "C:\\Users\\hugo\\Desktop\\jsonTest";
	
	public static void main(String[] args) {
		Graph graph = new SingleGraph("Test1");
		String id = null, know = null;
		Node n = null;
		
		graph.display();
		
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.addAttribute("ui.stylesheet",
			"edge { fill-color: grey; }");

		try {

			JsonFactory jfactory = new JsonFactory();

			/*** read from file ***/
			JsonParser jParser = jfactory.createParser(new File(
					"C:\\Users\\hugo\\Desktop\\jsonTest"));

			while (jParser.getCurrentToken() != jParser.nextToken()) {
				// loop until EOF !!! SALE !!! A REFAIRE !!!

				// loop until token equal to "}"
				while (jParser.nextToken() != JsonToken.END_OBJECT) {

					String fieldname = jParser.getCurrentName();
					if ("name".equals(fieldname)) {

						// current token is "name",
						// move to next, which is "name"'s value
						jParser.nextToken();
						id = jParser.getText();
						n = graph.addNode(id);
						n.setAttribute("ui.label", "nom : " + id);

					}

					if ("age".equals(fieldname)) {

						// current token is "age",
						// move to next, which is "age"'s value
						jParser.nextToken();
						
						n.addAttribute("age", jParser.getIntValue());

					}

					if ("know".equals(fieldname)) {

						// current token is "know",
						// move to next, which is "know"'s value
						jParser.nextToken();
						know = jParser.getText();
						if(!know.equals("none")){
							graph.addEdge(id + "-" + know, id, know, true);
							graph.getEdge(id + "-" + know).setAttribute("ui.label", id + "-" + know);
						}
						

					}
					
					if ("knows".equals(fieldname)) {
						 
						  jParser.nextToken(); // current token is "[", move next
				 
						  // knows is array, loop until token equal to "]"
						  while (jParser.nextToken() != JsonToken.END_ARRAY) {
				 
							know = jParser.getText();
							graph.addEdge(id + "-" + know, id, know, true);
							graph.getEdge(id + "-" + know).setAttribute("ui.label", id + "-" + know);
				 
						  }
				 
						}

				}
			}
			jParser.close();

		} catch (JsonGenerationException e) {

			e.printStackTrace();

		} catch (JsonMappingException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}