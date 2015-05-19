package interfaceGraphiqueTest;

import interfaceGraphique.GraphModifier;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

import jsonAndGS.MyJsonGenerator;
import junit.framework.TestCase;

public class GraphModifierTest extends TestCase {
	
	public void testSetNodeClass () {
		Graph graphTest = new MultiGraph("graphTest");
		Node nodeTest1 = graphTest.addNode("test1");
		nodeTest1.addAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE, true);
		nodeTest1.addAttribute(MyJsonGenerator.FORMAT_NODE_FINAL, false);
		GraphModifier.setNodeClass(graphTest, nodeTest1);
		assertEquals(MyJsonGenerator.FORMAT_NODE_SOURCE, graphTest.getNode("test1").getAttribute("ui.class"));
		
		Node nodeTest2 = graphTest.addNode("test2");
		nodeTest2.addAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE, false);
		nodeTest2.addAttribute(MyJsonGenerator.FORMAT_NODE_FINAL, true);
		GraphModifier.setNodeClass(graphTest, nodeTest2);
		assertEquals(MyJsonGenerator.FORMAT_NODE_FINAL, graphTest.getNode("test2").getAttribute("ui.class"));
		
		Node nodeTest3 = graphTest.addNode("test3");
		nodeTest3.addAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE, true);
		nodeTest3.addAttribute(MyJsonGenerator.FORMAT_NODE_FINAL, true);
		GraphModifier.setNodeClass(graphTest, nodeTest3);
		assertEquals(MyJsonGenerator.FORMAT_NODE_SOURCE + MyJsonGenerator.FORMAT_NODE_FINAL, graphTest.getNode("test3").getAttribute("ui.class"));
	}
	
	public void testSetNodesClass () {
		Graph graphTest = new MultiGraph("graphTest");
		Node nodeTest1 = graphTest.addNode("test1");
		nodeTest1.addAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE, true);
		nodeTest1.addAttribute(MyJsonGenerator.FORMAT_NODE_FINAL, false);
		
		Node nodeTest2 = graphTest.addNode("test2");
		nodeTest2.addAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE, false);
		nodeTest2.addAttribute(MyJsonGenerator.FORMAT_NODE_FINAL, true);
		
		Node nodeTest3 = graphTest.addNode("test3");
		nodeTest3.addAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE, true);
		nodeTest3.addAttribute(MyJsonGenerator.FORMAT_NODE_FINAL, true);
		
		GraphModifier.setNodesClass(graphTest);
		
		assertEquals(MyJsonGenerator.FORMAT_NODE_SOURCE, graphTest.getNode("test1").getAttribute("ui.class"));
		assertEquals(MyJsonGenerator.FORMAT_NODE_FINAL, graphTest.getNode("test2").getAttribute("ui.class"));
		assertEquals(MyJsonGenerator.FORMAT_NODE_SOURCE + MyJsonGenerator.FORMAT_NODE_FINAL, graphTest.getNode("test3").getAttribute("ui.class"));
	}
	
	public void testGenerateSprite(){
		Graph graphTest = new MultiGraph("graphTest");
		SpriteManager spriteManager = new SpriteManager(graphTest);
		Node nodeTest1 = graphTest.addNode("test1");
		Node nodeTest2 = graphTest.addNode("test2");
		Edge edgeTest3 = graphTest.addEdge("test3", nodeTest1, nodeTest2);
		GraphModifier.generateSprite(graphTest, spriteManager);
		Sprite spriteTest1 = spriteManager.getSprite(edgeTest3.getId());
		assertEquals(edgeTest3, spriteTest1.getAttachment());
	}
	
	//TODO Test graphToGraph si on garde la méthode
	
	//TODO autres tests

}
