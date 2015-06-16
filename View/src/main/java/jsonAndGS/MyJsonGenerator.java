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

/**
 * <b>MyJsonGenerator est la classe permettant de générer un fichier Json a
 * partir d'une liste de nodes et d'une liste de edges</b>
 * 
 * @see MyListNodes
 * @see MyListEdges
 * 
 * @author hugo
 * @version 1.0
 */
public class MyJsonGenerator {

	/**
	 * Le format du champ node dans le fichier Json
	 */
	public static final String FORMAT_NODES = "Nodes";

	/**
	 * Le format du champ edge dans le fichier Json
	 */
	public static final String FORMAT_EDGES = "Edges";

	/**
	 * Le format du champ name des nodes dans le fichier Json
	 */
	public static final String FORMAT_NODE_NAME = "Name";

	/**
	 * Le format du champ source des nodes dans le fichier Json
	 */
	public static final String FORMAT_NODE_SOURCE = "Source";

	/**
	 * Le format du champ final des nodes dans le fichier Json
	 */
	public static final String FORMAT_NODE_FINAL = "Final";

	/**
	 * Le format du champ attribut des nodes dans le fichier Json
	 */
	public static final String FORMAT_NODE_ATTRIBUT = "Attribut ";

	/**
	 * Le format du champ label des edges dans le fichier Json
	 */
	public static final String FORMAT_EDGE_LABEL = "Label";

	/**
	 * Le format du champ begin node des edges dans le fichier Json
	 */
	public static final String FORMAT_EDGE_BEGIN_NODE = "NodeB";

	/**
	 * Le format du champ end node des edges dans le fichier Json
	 */
	public static final String FORMAT_EDGE_END_NODE = "NodeE";

	/**
	 * Le format du champ action des edges dans le fichier Json
	 */
	public static final String FORMAT_EDGE_ACTION = "Action";

	/**
	 * Le format du champ attribut des edges dans le fichier Json
	 */
	public static final String FORMAT_EDGE_ATTRIBUT = "Attribut ";

	/**
	 * Génère les nodes dans le fichier Json
	 * 
	 * @param jGenerator
	 *            Le générateur de fichier Json utilisée
	 * @param mLN
	 *            La liste des nodes à générer
	 * 
	 * @see JsonGenerator
	 * @see MyListNodes
	 * 
	 * @since 1.0
	 */
	public void generateNodes(JsonGenerator jGenerator, MyListNodes mLN) {
		for (MyJsonNode node : mLN.get_listNodes()) {
			try {
				jGenerator.writeStartObject(); // {
				jGenerator.writeStringField(FORMAT_NODE_NAME, node.get_name());
				jGenerator.writeBooleanField(FORMAT_NODE_SOURCE,
						node.is_source());
				jGenerator
						.writeBooleanField(FORMAT_NODE_FINAL, node.is_final());
				int cpt = 1;
				for (String attribut : node.get_attributs()) {
					jGenerator.writeStringField(FORMAT_NODE_ATTRIBUT + cpt++,
							attribut);
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

	/**
	 * Génère les edges dans le fichier Json
	 * 
	 * @param jGenerator
	 *            L'instance du générateur de fichier Json utilisée
	 * 
	 * @param mLE
	 *            La liste des edges à générer
	 * 
	 * @see JsonGenerator
	 * @see MyListEdges
	 * 
	 * @since 1.0
	 */
	public void generateEdges(JsonGenerator jGenerator, MyListEdges mLE) {
		for (MyJsonEdge edge : mLE.get_listEdges()) {
			try {
				jGenerator.writeStartObject(); // {
				jGenerator
						.writeStringField(FORMAT_EDGE_LABEL, edge.get_label());
				jGenerator.writeStringField(FORMAT_EDGE_BEGIN_NODE,
						edge.get_nodeB());
				jGenerator.writeStringField(FORMAT_EDGE_END_NODE,
						edge.get_nodeE());
				jGenerator.writeStringField(FORMAT_EDGE_ACTION,
						edge.get_action());
				int cpt = 1;
				for (String attribut : edge.get_attributs()) {
					jGenerator.writeStringField(FORMAT_EDGE_ATTRIBUT + cpt++,
							attribut);
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

	/**
	 * Génére un fichier Json avec un tableau de nodes et un tableau de edges
	 * 
	 * @param mLN
	 *            La liste des nodes à générer
	 * 
	 * @param mLE
	 *            La liste des edges à générer
	 * 
	 * @return Le fichier Json généré sous la forme d'un File
	 * 
	 * @see MyListNodes
	 * @see MyListEdges
	 * @see File
	 * 
	 * @since 1.0
	 */
	public File generateJson(MyListNodes mLN, MyListEdges mLE) {

		// TODO changer le chemin d'acces lors de la release
		JFileChooser dialogue = new JFileChooser(new File(
				"./src/test/resources/jsonAndGSTest"));
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

				/* write to file */
				JsonGenerator jGenerator = jfactory.createGenerator(fichier,
						JsonEncoding.UTF8);
				jGenerator.writeStartObject(); // {

				jGenerator.writeFieldName(FORMAT_NODES); // "Nodes" :
				jGenerator.writeStartArray(); // [
				generateNodes(jGenerator, mLN);
				jGenerator.writeEndArray(); // ]

				jGenerator.writeFieldName(FORMAT_EDGES); // "Edges" :
				jGenerator.writeStartArray(); // [
				generateEdges(jGenerator, mLE);
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