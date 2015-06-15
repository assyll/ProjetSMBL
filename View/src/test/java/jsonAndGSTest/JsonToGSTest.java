package jsonAndGSTest;

import java.io.File;
import java.io.IOException;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;

import jsonAndGS.FileFormatException;
import jsonAndGS.JsonToGS;
import jsonAndGS.MyJsonGenerator;
import junit.framework.TestCase;

public class JsonToGSTest extends TestCase {
	
	public void testGenerateNode(){
		Graph graph = new MultiGraph("graphTest");
		
		File fileTest = new File("./src/test/resources/jsonAndGSTest/TestNode");
		
		JsonFactory jfactory = new JsonFactory();
		
		JsonParser parser;
		try {
			parser = jfactory.createParser(fileTest);
			parser.nextToken();
			JsonToGS.generateNodes(parser, graph);
			
			assertEquals(graph.getNodeCount(), 1);
			
			Node node = graph.getNode("node 1");
			assertNotNull(node);
			
			assertEquals(true, node.getAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE));
			
			assertEquals(false, node.getAttribute(MyJsonGenerator.FORMAT_NODE_FINAL));
			
			assertEquals("att 1", node.getAttribute(MyJsonGenerator.FORMAT_NODE_ATTRIBUT + "1"));
			
			parser.close();
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FileFormatException e) {
			e.printStackTrace();
		}
		
	}
	
	public void testGenerateEdge(){
		Graph graph = new MultiGraph("graph");
		
		File fileTest = new File("./src/test/resources/jsonAndGSTest/TestEdge");
		
		JsonFactory jfactory = new JsonFactory();
		
		JsonParser parser;
		try {
			parser = jfactory.createParser(fileTest);
			parser.nextToken();
			JsonToGS.generateNodes(parser, graph);
			parser.nextToken();
			JsonToGS.generateNodes(parser, graph);
			parser.nextToken();
			JsonToGS.generateEdges(parser, graph);
			
			assertEquals(graph.getEdgeCount(), 1);
			
			Edge edge = graph.getEdge("edge 1");
			assertNotNull(edge);
			
			assertEquals("node 1", edge.getSourceNode().getId());
			
			assertEquals("node 2", edge.getTargetNode().getId());
			
			assertEquals("action !", edge.getAttribute("ui.label"));
			
			assertEquals("att 1", edge.getAttribute(MyJsonGenerator.FORMAT_EDGE_ATTRIBUT + "1"));
			
			parser.close();
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FileFormatException e) {
			e.printStackTrace();
		}
		
	}
	
	public void testGenerateJson() {
		
		File fileTest = new File("./src/test/resources/jsonAndGSTest/TestJson");
		
		JsonFactory jfactory = new JsonFactory();
		
		JsonParser parser;
		try {
			parser = jfactory.createParser(fileTest);
			parser.nextToken();
			Graph graph = JsonToGS.generateGraph(fileTest.getAbsolutePath(), "graphTest");
			
			assertEquals(graph.getEdgeCount(), 1);
			
			Edge edge = graph.getEdge("edge 1");
			assertNotNull(edge);
			
			assertEquals("node 1", edge.getSourceNode().getId());
			
			assertEquals("node 2", edge.getTargetNode().getId());
			
			assertEquals("action !", edge.getAttribute("ui.label"));
			
			assertEquals("att 1", edge.getAttribute(MyJsonGenerator.FORMAT_EDGE_ATTRIBUT + "1"));
			
			assertEquals(2, graph.getNodeCount());
			
			Node node = graph.getNode("node 1");
			assertNotNull(node);
			
			assertEquals(true, node.getAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE));
			
			assertEquals(false, node.getAttribute(MyJsonGenerator.FORMAT_NODE_FINAL));
			
			assertEquals("att 1", node.getAttribute(MyJsonGenerator.FORMAT_NODE_ATTRIBUT + "1"));
			
			node = graph.getNode("node 2");
			assertNotNull(node);
			
			assertEquals(true, node.getAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE));
			
			assertEquals(false, node.getAttribute(MyJsonGenerator.FORMAT_NODE_FINAL));
			
			assertEquals("att 1", node.getAttribute(MyJsonGenerator.FORMAT_NODE_ATTRIBUT + "1"));
			
			parser.close();
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FileFormatException e) {
			e.printStackTrace();
		}
	}

}
