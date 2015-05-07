package jSonAndGS;

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

public class JSonToGS {
	final static String FILE_PATH = "C:\\Users\\hugo\\Desktop\\jsonTest";

	public static void generateNode(JsonParser jParser, Graph graph) {
		String name;
		Node node;
		try {
			jParser.nextToken();
			String fieldname = jParser.getCurrentName();
			if ("Name".equals(fieldname)) {
				// current token is "Name",
				// move to next, which is "Name"'s value
				jParser.nextToken();
				name = jParser.getText();
				node = graph.addNode(name);
				node.setAttribute("ui.label", name);
			} else {
				throw new FormatFichierException(
						"Le format du fichier sélectionné est invalide");
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
			if ("Label".equals(fieldname)) {
				// current token is "Label",
				// move to next, which is "Label"'s value
				jParser.nextToken();
				label = jParser.getText();
			} else {
				System.out.println(label);
				throw new FormatFichierException(
						"Le format du fichier sélectionné est invalide");
			}
			jParser.nextToken();
			fieldname = jParser.getCurrentName();
			if ("NoeudD".equals(fieldname)) {
				// current token is "NoeudD",
				// move to next, which is "NoeudD"'s value
				jParser.nextToken();
				noeudD = jParser.getText();
			} else {
				throw new FormatFichierException(
						"Le format du fichier sélectionné est invalide");
			}
			jParser.nextToken();
			fieldname = jParser.getCurrentName();
			if ("NoeudA".equals(fieldname)) {
				// current token is "NoeudA",
				// move to next, which is "NoeudA"'s value
				jParser.nextToken();
				noeudA = jParser.getText();
				edge = graph.addEdge(label, noeudD, noeudA, true);
				edge.setAttribute("ui.label", label);
			} else {
				throw new FormatFichierException(
						"Le format du fichier sélectionné est invalide");
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

	public static void generateGraph() {
		Graph graph = new SingleGraph("graph");

		graph.display();

		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.addAttribute("ui.stylesheet", "edge { fill-color: grey; }");

		try {

			JsonFactory jfactory = new JsonFactory();

			/*** read from file ***/
			JsonParser jParser = jfactory.createParser(new File(FILE_PATH));
			jParser.nextToken();
			jParser.nextToken();
			String fieldname = jParser.getCurrentName();

			if ("Nodes".equals(fieldname)) {
				jParser.nextToken(); // current token is "[", move next
				while (jParser.nextToken() != JsonToken.END_ARRAY) {
					// nodes is array, loop until token equal to "]"
					generateNode(jParser, graph);
				}
			} else {
				System.out.println(fieldname);
				throw new FormatFichierException(
						"Le format du fichier sélectionné est invalide");
			}

			jParser.nextToken();
			fieldname = jParser.getCurrentName();

			if ("Transitions".equals(fieldname)) {
				jParser.nextToken(); // current token is "[", move next
				while (jParser.nextToken() != JsonToken.END_ARRAY) {
					// transitions is array, loop until token equal to "]"
					generateTransitions(jParser, graph);
				}
			} else {
				throw new FormatFichierException(
						"Le format du fichier sélectionné est invalide");
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

	}

	public static void main(String[] args) {
		generateGraph();
	}

}