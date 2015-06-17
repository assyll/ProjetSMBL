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

/**
 * <b>JsonToGS est la classe permettant de générer un graphe à partir d'un
 * fichier Json</b>
 * 
 * @see MyJsonGenerator
 * 
 * @author hugo
 * @version 1.0
 */
public class JsonToGS {

	/**
	 * Le message d'erreur affiché lors que le format du fichier Json
	 * sélectionné n'est pas celui convenu
	 */
	public static final String FILE_FORMAT_ERROR = "Le format du fichier sélectionné est invalide";

	/**
	 * Génère les nodes dans le graphe
	 * 
	 * @param jParser
	 *            Le parser permettant de recupérer les informations du fichier
	 *            Json
	 * @param graph
	 *            Le graph dans lequel générer les nodes
	 * @throws JsonParseException
	 *             Si le format du fichier Json est incorrect
	 * @throws IOException
	 *             Si il y'a un probleme avec le fichier
	 * @throws FileFormatException
	 *             Si le format du fichier Json n'est pas celui convenu
	 * 
	 * @see JsonParser
	 * @see Graph
	 * 
	 * @since 1.0
	 */
	public static void generateNodes(JsonParser jParser, Graph graph)
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

	/**
	 * Génère les edges dans le graphe
	 * 
	 * @param jParser
	 *            Le parser permettant de recupérer les informations du fichier
	 *            Json
	 * @param graph
	 *            Le graph dans lequel générer les edges
	 * @throws JsonParseException
	 *             Si le format du fichier Json est incorrect
	 * @throws IOException
	 *             Si il y'a un probleme avec le fichier
	 * @throws FileFormatException
	 *             Si le format du fichier Json n'est pas celui convenu
	 * 
	 * @see JsonParser
	 * @see Graph
	 * 
	 * @since 1.0
	 */
	public static void generateEdges(JsonParser jParser, Graph graph)
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
			edge.setAttribute(MyJsonGenerator.FORMAT_EDGE_LABEL, label);
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
			edge.setAttribute(fieldname, action);
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

	/**
	 * Génère le graphe à partir d'un fichier Json
	 * 
	 * @param filePath
	 *            Le chemin d'accés au fichier Json
	 * @param graphName
	 *            Le nom à donner au graphe généré
	 * 
	 * @return Le graphe généré
	 * 
	 * @throws JsonParseException
	 *             Si le format du fichier Json est incorrect
	 * @throws IOException
	 *             Si il y'a un probleme avec le fichier
	 * @throws FileFormatException
	 *             Si le format du fichier Json n'est pas celui convenu
	 * 
	 * @see MyJsonGenerator
	 * 
	 * @since 1.0
	 */
	public static Graph generateGraph(String filePath, String graphName)
			throws JsonParseException, IOException, FileFormatException {
		Graph graph = new MultiGraph(graphName);

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