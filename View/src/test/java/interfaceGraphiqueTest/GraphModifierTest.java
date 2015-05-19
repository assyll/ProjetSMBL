package interfaceGraphiqueTest;

import interfaceGraphique.GraphModifier;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import jsonAndGS.MyJsonGenerator;
import junit.framework.TestCase;

public class GraphModifierTest extends TestCase {
	
	public void testSetNodeClass () {
		Graph graphTest = new MultiGraph("graphTest");
		Node nodeTest1 = graphTest.addNode("test1");
		nodeTest1.addAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE, true);
		nodeTest1.addAttribute(MyJsonGenerator.FORMAT_NODE_FINAL, false);
		GraphModifier.setNodeClass(graphTest);
		assertEquals(graphTest.getNode("test1").getAttribute("ui.class"), MyJsonGenerator.FORMAT_NODE_SOURCE);
		
		Node nodeTest2 = graphTest.addNode("test2");
		nodeTest2.addAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE, false);
		nodeTest2.addAttribute(MyJsonGenerator.FORMAT_NODE_FINAL, true);
		GraphModifier.setNodeClass(graphTest);
		assertEquals(graphTest.getNode("test2").getAttribute("ui.class"), MyJsonGenerator.FORMAT_NODE_FINAL);
		
		Node nodeTest3 = graphTest.addNode("test3");
		nodeTest3.addAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE, true);
		nodeTest3.addAttribute(MyJsonGenerator.FORMAT_NODE_FINAL, true);
		GraphModifier.setNodeClass(graphTest);
		assertEquals(graphTest.getNode("test3").getAttribute("ui.class"), MyJsonGenerator.FORMAT_NODE_SOURCE + MyJsonGenerator.FORMAT_NODE_FINAL);
	}
	
	//TODO Test graphToGraph si on garde la méthode
	
	//TODO autres tests

}
