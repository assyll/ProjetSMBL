package jsonAndGS;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;

public class MyJsonGenerator {
	static final String FORMAT_NODES = "Nodes";
	static final String FORMAT_EDGES = "Edges";
	static final String FORMAT_NODE_NAME = "Name";
	static final String FORMAT_NODE_ATTRIBUT = "Attribut ";
	static final String FORMAT_EDGE_LABEL = "Label";
	static final String FORMAT_EDGE_BEGIN_NODE = "NodeB";
	static final String FORMAT_EDGE_END_NODE = "NodeE";
	static final String FORMAT_EDGE_ATTRIBUT = "Attribut ";

	public void generateNodes(JsonGenerator jGenerator, MyListNodes mLN) {
		for (MyJsonNode node : mLN.get_listNodes()) {
			try {
				jGenerator.writeStartObject(); // {
				jGenerator.writeStringField(FORMAT_NODE_NAME, node.get_name());
				int cpt = 1;
				for (String attribut : node.get_attributs()) {
					jGenerator.writeStringField(FORMAT_NODE_ATTRIBUT + cpt++, attribut);
				}
				jGenerator.writeEndObject(); // }

			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void generateEdges(JsonGenerator jGenerator,
			MyListEdges mLE) {
		for (MyJsonEdge edge : mLE.get_listEdges()) {
			try {
				jGenerator.writeStartObject(); // {
				jGenerator.writeStringField(FORMAT_EDGE_LABEL, edge.get_label());
				jGenerator.writeStringField(FORMAT_EDGE_BEGIN_NODE, edge.get_nodeB());
				jGenerator.writeStringField(FORMAT_EDGE_END_NODE, edge.get_nodeE());
				int cpt = 1;
				for (String attribut : edge.get_attributs()) {
					jGenerator.writeStringField(FORMAT_EDGE_ATTRIBUT + cpt++, attribut);
				}
				jGenerator.writeEndObject(); // }

			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public File generateJson(MyListNodes mLN, MyListEdges mLT) {

		JFileChooser dialogue = new JFileChooser(new File("."));
		PrintWriter sortie = null;
		File fichier;

		if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			fichier = dialogue.getSelectedFile();
			try {
				sortie = new PrintWriter(
						new FileWriter(fichier.getPath(), true));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			sortie.close();

			try {

				JsonFactory jfactory = new JsonFactory();

				/*** write to file ***/
				JsonGenerator jGenerator = jfactory.createGenerator(fichier,
						JsonEncoding.UTF8);
				jGenerator.writeStartObject(); // {

				jGenerator.writeFieldName(FORMAT_NODES); // "Nodes" :
				jGenerator.writeStartArray(); // [
				generateNodes(jGenerator, mLN);
				jGenerator.writeEndArray(); // ]

				jGenerator.writeFieldName(FORMAT_EDGES); // "Edges" :
				jGenerator.writeStartArray(); // [
				generateEdges(jGenerator, mLT);
				jGenerator.writeEndArray(); // ]

				jGenerator.writeEndObject(); // }

				jGenerator.close();

				return fichier;

			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}