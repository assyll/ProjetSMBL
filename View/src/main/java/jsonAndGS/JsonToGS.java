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

	static final String FILE_FORMAT_ERROR = "Le format du fichier s�lectionn� est invalide";

	public void generateNodes(JsonParser jParser, Graph graph) {
		String name, fieldname;
		Node node;

		try {
			jParser.nextToken();
			fieldname = jParser.getCurrentName();
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

	public void generateEdges(JsonParser jParser, Graph graph) {
		String label, nodeB, nodeE, action;
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
			} else {
				throw new FileFormatException(FILE_FORMAT_ERROR);
			}
			
			jParser.nextToken();
			fieldname = jParser.getCurrentName();
			if (MyJsonGenerator.FORMAT_EDGE_ACTION.equals(fieldname)) {
				// current token is "Action",
				// move to next, which is "Action"'s value
				jParser.nextToken();
				action = jParser.getText();
				edge.setAttribute("ui.label", action);
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

	public Graph generateGraph(String filePath) {
		Graph graph = new MultiGraph("graph");

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
					generateNodes(jParser, graph);
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

			// TODO APPEL FENETRE D'ALERTE QUAND EXCEPTION
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