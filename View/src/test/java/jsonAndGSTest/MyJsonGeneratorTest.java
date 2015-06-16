package jsonAndGSTest;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;

import jsonAndGS.MyJsonGenerator;
import jsonAndGS.MyJsonNode;
import jsonAndGS.MyJsonEdge;
import jsonAndGS.MyListNodes;
import jsonAndGS.MyListEdges;
import junit.framework.TestCase;

public class MyJsonGeneratorTest extends TestCase {

	final String testPathFile = "./src/test/resources/jsonAndGSTest/Temp";

	public void testGenerateNodes() {
		MyJsonGenerator generator = new MyJsonGenerator();

		MyJsonNode n1 = new MyJsonNode("node 1", true, false,
				new LinkedList<String>());
		n1.get_attributs().add("att 1");

		MyListNodes mLN = new MyListNodes();
		mLN.addNode(n1);

		try {

			File fileTest = new File(testPathFile);
			fileTest.deleteOnExit();
			JsonFactory jfactory = new JsonFactory();

			/*** write to file ***/
			JsonGenerator jGenerator = jfactory.createGenerator(fileTest,
					JsonEncoding.UTF8);
			generator.generateNodes(jGenerator, mLN);
			jGenerator.close();

			/*** read from file ***/
			JsonParser jParser = jfactory.createParser(new File(testPathFile));
			jParser.nextToken();
			jParser.nextToken();
			String fieldname = jParser.getCurrentName();
			assertEquals(MyJsonGenerator.FORMAT_NODE_NAME, fieldname);
			jParser.nextToken();
			String name = jParser.getText();
			assertEquals("node 1", name);

			jParser.nextToken();
			fieldname = jParser.getCurrentName();
			assertEquals(MyJsonGenerator.FORMAT_NODE_SOURCE, fieldname);
			jParser.nextToken();
			Boolean source = jParser.getBooleanValue();
			assertTrue(source);

			jParser.nextToken();
			fieldname = jParser.getCurrentName();
			assertEquals(MyJsonGenerator.FORMAT_NODE_FINAL, fieldname);
			jParser.nextToken();
			Boolean fin = jParser.getBooleanValue();
			assertFalse(fin);

			jParser.nextToken();
			fieldname = jParser.getCurrentName();
			assertEquals(MyJsonGenerator.FORMAT_NODE_ATTRIBUT + "1", fieldname);
			jParser.nextToken();
			String attribut = jParser.getText();
			assertEquals("att 1", attribut);

			assertEquals(JsonToken.END_OBJECT, jParser.nextToken());

			jParser.close();

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testGenerateEdges() {
		MyJsonGenerator generator = new MyJsonGenerator();

		MyJsonEdge e1 = new MyJsonEdge("edge 1", "node 1", "node 2",
				"action !", new LinkedList<String>());
		e1.get_attributs().add("att 1");

		MyListEdges mLE = new MyListEdges();
		mLE.addEdges(e1);

		try {

			File fileTest = new File(testPathFile);
			fileTest.deleteOnExit();
			JsonFactory jfactory = new JsonFactory();

			/*** write to file ***/
			JsonGenerator jGenerator = jfactory.createGenerator(fileTest,
					JsonEncoding.UTF8);
			generator.generateEdges(jGenerator, mLE);
			jGenerator.close();

			/*** read from file ***/
			JsonParser jParser = jfactory.createParser(fileTest);
			jParser.nextToken();
			jParser.nextToken();
			String fieldname = jParser.getCurrentName();
			assertEquals(MyJsonGenerator.FORMAT_EDGE_LABEL, fieldname);
			jParser.nextToken();
			String label = jParser.getText();
			assertEquals("edge 1", label);

			jParser.nextToken();
			fieldname = jParser.getCurrentName();
			assertEquals(MyJsonGenerator.FORMAT_EDGE_BEGIN_NODE, fieldname);
			jParser.nextToken();
			String nodeB = jParser.getText();
			assertEquals("node 1", nodeB);

			jParser.nextToken();
			fieldname = jParser.getCurrentName();
			assertEquals(MyJsonGenerator.FORMAT_EDGE_END_NODE, fieldname);
			jParser.nextToken();
			String nodeE = jParser.getText();
			assertEquals("node 2", nodeE);

			jParser.nextToken();
			fieldname = jParser.getCurrentName();
			assertEquals(MyJsonGenerator.FORMAT_EDGE_ACTION, fieldname);
			jParser.nextToken();
			String attribut = jParser.getText();
			assertEquals("action !", attribut);

			jParser.nextToken();
			fieldname = jParser.getCurrentName();
			assertEquals(MyJsonGenerator.FORMAT_EDGE_ATTRIBUT + "1", fieldname);
			jParser.nextToken();
			attribut = jParser.getText();
			assertEquals("att 1", attribut);

			assertEquals(JsonToken.END_OBJECT, jParser.nextToken());

			jParser.close();

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testGenerateJson() {
		MyJsonGenerator generator = new MyJsonGenerator();

		MyJsonNode n1 = new MyJsonNode("node 1", true, false,
				new LinkedList<String>());
		n1.get_attributs().add("att 1");
		MyJsonNode n2 = new MyJsonNode("node 2", true, false,
				new LinkedList<String>());
		n2.get_attributs().add("att 1");

		MyListNodes mLN = new MyListNodes();
		mLN.addNode(n1, n2);

		MyJsonEdge e1 = new MyJsonEdge("edge 1", "node 1", "node 2",
				"action !", new LinkedList<String>());
		e1.get_attributs().add("att 1");

		MyListEdges mLE = new MyListEdges();
		mLE.addEdges(e1);

		try {

			/*** write to file ***/
			File file = generator.generateJson(mLN, mLE);
			file.deleteOnExit();

			/*** read from file ***/
			JsonFactory jfactory = new JsonFactory();
			JsonParser jParser = jfactory.createParser(file);
			jParser.nextToken();
			jParser.nextToken();

			String fieldname = jParser.getCurrentName();
			assertEquals(MyJsonGenerator.FORMAT_NODES, fieldname);
			assertEquals(JsonToken.START_ARRAY, jParser.nextToken());

			while (jParser.nextToken() != JsonToken.END_ARRAY) {
			}

			jParser.nextToken();
			fieldname = jParser.getCurrentName();
			assertEquals(MyJsonGenerator.FORMAT_EDGES, fieldname);
			assertEquals(JsonToken.START_ARRAY, jParser.nextToken());

			while (jParser.nextToken() != JsonToken.END_ARRAY) {
			}

			assertEquals(JsonToken.END_OBJECT, jParser.nextToken());

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
