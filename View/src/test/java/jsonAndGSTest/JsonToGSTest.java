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
		JsonToGS jTGS = new JsonToGS();
		Graph graph = new MultiGraph("graph");
		
		File fileTest = new File("./src/test/resources/TestNode");
		
		JsonFactory jfactory = new JsonFactory();
		
		JsonParser parser;
		try {
			parser = jfactory.createParser(fileTest);
			parser.nextToken();
			jTGS.generateNodes(parser, graph);
			
			assertEquals(graph.getNodeCount(), 1);
			
			Node node = graph.getNode("node 1");
			assertNotNull(node);
			
			assertEquals(node.getAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE), true);
			
			assertEquals(node.getAttribute(MyJsonGenerator.FORMAT_NODE_FINAL), false);
			
			assertEquals(node.getAttribute(MyJsonGenerator.FORMAT_NODE_ATTRIBUT + "1"), "att 1");
			
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
		JsonToGS jTGS = new JsonToGS();
		Graph graph = new MultiGraph("graph");
		
		File fileTest = new File("./src/test/resources/TestEdge");
		
		JsonFactory jfactory = new JsonFactory();
		
		JsonParser parser;
		try {
			parser = jfactory.createParser(fileTest);
			parser.nextToken();
			jTGS.generateNodes(parser, graph);
			parser.nextToken();
			jTGS.generateNodes(parser, graph);
			parser.nextToken();
			jTGS.generateEdges(parser, graph);
			
			assertEquals(graph.getEdgeCount(), 1);
			
			Edge edge = graph.getEdge("edge 1");
			assertNotNull(edge);
			
			assertEquals(edge.getSourceNode().getId(), "node 1");
			
			assertEquals(edge.getTargetNode().getId(), "node 2");
			
			assertEquals(edge.getAttribute("ui.label"), "action !");
			
			assertEquals(edge.getAttribute(MyJsonGenerator.FORMAT_EDGE_ATTRIBUT + "1"), "att 1");
			
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
		JsonToGS jTGS = new JsonToGS();
		
		File fileTest = new File("./src/test/resources/TestJson");
		
		JsonFactory jfactory = new JsonFactory();
		
		JsonParser parser;
		try {
			parser = jfactory.createParser(fileTest);
			parser.nextToken();
			Graph graph = jTGS.generateGraph(fileTest.getAbsolutePath());
			
			assertEquals(graph.getEdgeCount(), 1);
			
			Edge edge = graph.getEdge("edge 1");
			assertNotNull(edge);
			
			assertEquals(edge.getSourceNode().getId(), "node 1");
			
			assertEquals(edge.getTargetNode().getId(), "node 2");
			
			assertEquals(edge.getAttribute("ui.label"), "action !");
			
			assertEquals(edge.getAttribute(MyJsonGenerator.FORMAT_EDGE_ATTRIBUT + "1"), "att 1");
			
			assertEquals(graph.getNodeCount(), 2);
			
			Node node = graph.getNode("node 1");
			assertNotNull(node);
			
			assertEquals(node.getAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE), true);
			
			assertEquals(node.getAttribute(MyJsonGenerator.FORMAT_NODE_FINAL), false);
			
			assertEquals(node.getAttribute(MyJsonGenerator.FORMAT_NODE_ATTRIBUT + "1"), "att 1");
			
			node = graph.getNode("node 2");
			assertNotNull(node);
			
			assertEquals(node.getAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE), true);
			
			assertEquals(node.getAttribute(MyJsonGenerator.FORMAT_NODE_FINAL), false);
			
			assertEquals(node.getAttribute(MyJsonGenerator.FORMAT_NODE_ATTRIBUT + "1"), "att 1");
			
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
