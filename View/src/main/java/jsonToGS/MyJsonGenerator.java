package jsonToGS;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;

public class MyJsonGenerator {
	final static String FILE_PATH = "C:\\Users\\hugo\\Desktop\\jsonTest";

	public static void generateNodes(JsonGenerator jGenerator, MyListNodes mLN) {
		for (MyJsonNode node : mLN.get_listNodes()) {
			try {
				jGenerator.writeStartObject(); // {
				jGenerator.writeStringField("Name", node.get_name());
				int cpt = 1;
				for (String attribut : node.get_attributs()) {
					jGenerator.writeStringField("Attribut " + cpt++, attribut);
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

	public static void generateTransitions(JsonGenerator jGenerator,
			MyListTransitions mLT) {
		for (MyJsonTransition transition : mLT.get_listTransitions()) {
			try {
				jGenerator.writeStartObject(); // {
				jGenerator.writeStringField("Label", transition.get_label());
				jGenerator.writeStringField("NoeudD", transition.get_noeudD());
				jGenerator.writeStringField("NoeudA", transition.get_noeudA());
				int cpt = 1;
				for (String attribut : transition.get_attributs()) {
					jGenerator.writeStringField("Attribut " + cpt++, attribut);
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

	public static void generateJson(MyListNodes mLN, MyListTransitions mLT) {
		try {

			JsonFactory jfactory = new JsonFactory();

			/*** write to file ***/
			JsonGenerator jGenerator = jfactory.createGenerator(new File(
					FILE_PATH), JsonEncoding.UTF8);
			jGenerator.writeStartObject(); // {

			jGenerator.writeFieldName("Nodes"); // "Nodes" :
			jGenerator.writeStartArray(); // [
			generateNodes(jGenerator, mLN);
			jGenerator.writeEndArray(); // ]

			jGenerator.writeFieldName("Transitions"); // "Transitions" :
			jGenerator.writeStartArray(); // [
			generateTransitions(jGenerator, mLT);
			jGenerator.writeEndArray(); // ]

			jGenerator.writeEndObject(); // }

			jGenerator.close();

		} catch (JsonGenerationException e) {

			e.printStackTrace();

		} catch (JsonMappingException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public static void main(String[] args) {

		MyListNodes mLN = new MyListNodes();
		MyListTransitions mLT = new MyListTransitions();

		MyJsonNode n1 = new MyJsonNode("node 1", new LinkedList<String>());
		n1.get_attributs().add("att 1");
		n1.get_attributs().add("att 2");
		MyJsonNode n2 = new MyJsonNode("node 2", new LinkedList<String>());
		n2.get_attributs().add("att 1");
		n2.get_attributs().add("att 2");
		MyJsonNode n3 = new MyJsonNode("node 3", new LinkedList<String>());
		n3.get_attributs().add("att 1");
		n3.get_attributs().add("att 2");
		mLN.addNode(n1, n2, n3);

		MyJsonTransition t1 = new MyJsonTransition("transition 1", "node 1",
				"node 2", new LinkedList<String>());
		t1.get_attributs().add("att 1");
		t1.get_attributs().add("att 2");
		MyJsonTransition t2 = new MyJsonTransition("transition 2", "node 2",
				"node 3", new LinkedList<String>());
		t2.get_attributs().add("att 1");
		t2.get_attributs().add("att 2");
		MyJsonTransition t3 = new MyJsonTransition("transition 3", "node 1",
				"node 3", new LinkedList<String>());
		t3.get_attributs().add("att 1");
		t3.get_attributs().add("att 2");
		mLT.addTransition(t1, t2, t3);

		generateJson(mLN, mLT);
	}

}