package jsonAndGS;

import java.io.File;
import java.io.IOException;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;

public class JsonToGS {
	
	static final String FILE_FORMAT_ERROR = "Le format du fichier sélectionné est invalide";

	public static void generateNode(JsonParser jParser, Graph graph) {
		String name;
		Node node;
		
		try {
			jParser.nextToken();
			String fieldname = jParser.getCurrentName();
			if (MyJsonGenerator.FORMAT_NODE_NAME.equals(fieldname)) {
				// current token is "Name",
				// move to next, which is "Name"'s value
				jParser.nextToken();
				name = jParser.getText();
				node = graph.addNode(name);
				node.setAttribute("ui.label", name);
			} else {
				throw new FormatFichierException(
						FILE_FORMAT_ERROR);
			}

			// loop until token equal to "}"
			while (jParser.nextToken() != JsonToken.END_OBJECT) {
				fieldname = jParser.getCurrentName();
				jParser.nextToken();
				node.addAttribute(fieldname, jParser.getText());
			}
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FormatFichierException e) {
			e.printStackTrace();
		}
	}
	
	public static void generateTransitions(JsonParser jParser, Graph graph){
		String label = null, noeudD, noeudA;
		Edge edge;
		
		try {
			jParser.nextToken();
			String fieldname = jParser.getCurrentName();
			if (MyJsonGenerator.FORMAT_TRANSITION_LABEL.equals(fieldname)) {
				// current token is "Label",
				// move to next, which is "Label"'s value
				jParser.nextToken();
				label = jParser.getText();
			} else {
				throw new FormatFichierException(
						FILE_FORMAT_ERROR);
			}
			jParser.nextToken();
			fieldname = jParser.getCurrentName();
			if (MyJsonGenerator.FORMAT_TRANSITION_BEGIN_NODE.equals(fieldname)) {
				// current token is "NoeudD",
				// move to next, which is "NoeudD"'s value
				jParser.nextToken();
				noeudD = jParser.getText();
			} else {
				throw new FormatFichierException(
						FILE_FORMAT_ERROR);
			}
			jParser.nextToken();
			fieldname = jParser.getCurrentName();
			if (MyJsonGenerator.FORMAT_TRANSITION_END_NODE.equals(fieldname)) {
				// current token is "NoeudA",
				// move to next, which is "NoeudA"'s value
				jParser.nextToken();
				noeudA = jParser.getText();
				edge = graph.addEdge(label, noeudD, noeudA, true);
				edge.setAttribute("ui.label", label);
			} else {
				throw new FormatFichierException(
						FILE_FORMAT_ERROR);
			}

			// loop until token equal to "}"
			while (jParser.nextToken() != JsonToken.END_OBJECT) {
				fieldname = jParser.getCurrentName();
				jParser.nextToken();
				edge.addAttribute(fieldname, jParser.getText());
			}
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FormatFichierException e) {
			e.printStackTrace();
		}
	}

	public static Graph generateGraph(String filePath) {
		Graph graph = new SingleGraph("graph");

		/*graph.display();

		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.addAttribute("ui.stylesheet", "edge { fill-color: grey; }");*/

		try {

			JsonFactory jfactory = new JsonFactory();

			/*** read from file ***/
			JsonParser jParser = jfactory.createParser(new File(filePath));
			jParser.nextToken();
			jParser.nextToken();
			String fieldname = jParser.getCurrentName();

			if (MyJsonGenerator.FORMAT_NODES.equals(fieldname)) {
				jParser.nextToken(); // current token is "[", move next
				while (jParser.nextToken() != JsonToken.END_ARRAY) {
					// nodes is array, loop until token equal to "]"
					generateNode(jParser, graph);
				}
			} else {
				throw new FormatFichierException(
						FILE_FORMAT_ERROR);
			}

			jParser.nextToken();
			fieldname = jParser.getCurrentName();

			if (MyJsonGenerator.FORMAT_TRANSITIONS.equals(fieldname)) {
				jParser.nextToken(); // current token is "[", move next
				while (jParser.nextToken() != JsonToken.END_ARRAY) {
					// transitions is array, loop until token equal to "]"
					generateTransitions(jParser, graph);
				}
			} else {
				throw new FormatFichierException(
						FILE_FORMAT_ERROR);
			}
			
			jParser.close();

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FormatFichierException e) {
			e.printStackTrace();
		}
		
		return graph;

	}

}