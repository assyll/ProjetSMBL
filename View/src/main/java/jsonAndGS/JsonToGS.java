package jsonAndGS;

import java.io.File;
import java.io.IOException;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

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
				throw new FileFormatException(FILE_FORMAT_ERROR);
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
		} catch (FileFormatException e) {
			e.printStackTrace();
		}
	}

	public static void generateEdges(JsonParser jParser, Graph graph) {
		String label, nodeB, nodeE;
		Edge edge;

		try {
			jParser.nextToken();
			String fieldname = jParser.getCurrentName();
			if (MyJsonGenerator.FORMAT_EDGE_LABEL.equals(fieldname)) {
				// current token is "Label",
				// move to next, which is "Label"'s value
				jParser.nextToken();
				label = jParser.getText();
			} else {
				throw new FileFormatException(FILE_FORMAT_ERROR);
			}
			jParser.nextToken();
			fieldname = jParser.getCurrentName();
			if (MyJsonGenerator.FORMAT_EDGE_BEGIN_NODE.equals(fieldname)) {
				// current token is "NodeB",
				// move to next, which is "NodeB"'s value
				jParser.nextToken();
				nodeB = jParser.getText();
			} else {
				throw new FileFormatException(FILE_FORMAT_ERROR);
			}
			jParser.nextToken();
			fieldname = jParser.getCurrentName();
			if (MyJsonGenerator.FORMAT_EDGE_END_NODE.equals(fieldname)) {
				// current token is "NodeE",
				// move to next, which is "NodeE"'s value
				jParser.nextToken();
				nodeE = jParser.getText();
				edge = graph.addEdge(label, nodeB, nodeE, true);
				edge.setAttribute("ui.label", label);
			} else {
				throw new FileFormatException(FILE_FORMAT_ERROR);
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
		} catch (FileFormatException e) {
			e.printStackTrace();
		}
	}

	public static Graph generateGraph(String filePath) {
		Graph graph = new MultiGraph("graph");

		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.addAttribute("ui.stylesheet", "edge { fill-color: grey; }");

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
				throw new FileFormatException(FILE_FORMAT_ERROR);
			}

			jParser.nextToken();
			fieldname = jParser.getCurrentName();

			if (MyJsonGenerator.FORMAT_EDGES.equals(fieldname)) {
				jParser.nextToken(); // current token is "[", move next
				while (jParser.nextToken() != JsonToken.END_ARRAY) {
					// transitions is array, loop until token equal to "]"
					generateEdges(jParser, graph);
				}
			} else {
				throw new FileFormatException(FILE_FORMAT_ERROR);
			}

			jParser.close();
			
			//TODO APPEL FENETRE D'ALERTE QUAND EXCEPTION
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FileFormatException e) {
			e.printStackTrace();
		}

		return graph;

	}

}