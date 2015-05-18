package jsonAndGS;

import java.io.File;
import java.io.IOException;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class JsonToGS {

	public static final String FILE_FORMAT_ERROR = "Le format du fichier sélectionné est invalide";

	public void generateNodes(JsonParser jParser, Graph graph)
			throws JsonParseException, IOException, FileFormatException {
		String name, fieldname;
		boolean source, fin;
		Node node;

		// try {
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
		
		jParser.nextToken();
		fieldname = jParser.getCurrentName();
		if (MyJsonGenerator.FORMAT_NODE_SOURCE.equals(fieldname)) {
			// current token is "Source",
			// move to next, which is "Source"'s value
			jParser.nextToken();
			source = jParser.getBooleanValue();
			node.addAttribute(fieldname, source);
		} else {
			throw new FileFormatException(FILE_FORMAT_ERROR);
		}
		
		jParser.nextToken();
		fieldname = jParser.getCurrentName();
		if (MyJsonGenerator.FORMAT_NODE_FINAL.equals(fieldname)) {
			// current token is "Final",
			// move to next, which is "Final"'s value
			jParser.nextToken();
			fin = jParser.getBooleanValue();
			node.addAttribute(fieldname, fin);
		} else {
			throw new FileFormatException(FILE_FORMAT_ERROR);
		}

		// loop until token equal to "}"
		while (jParser.nextToken() != JsonToken.END_OBJECT) {
			fieldname = jParser.getCurrentName();
			jParser.nextToken();
			node.addAttribute(fieldname, jParser.getText());
		}
	}

	public void generateEdges(JsonParser jParser, Graph graph)
			throws JsonParseException, IOException, FileFormatException {
		String label, nodeB, nodeE, action;
		Edge edge;

		// try {
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
	}

	public Graph generateGraph(String filePath) throws JsonParseException,
			IOException, FileFormatException {
		Graph graph = new MultiGraph("graph");

		// try {

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

		// TODO Verifier l'inutilité des erreurs JsonGenerationException et
		// JsonMappingException
		
		return graph;

	}

}