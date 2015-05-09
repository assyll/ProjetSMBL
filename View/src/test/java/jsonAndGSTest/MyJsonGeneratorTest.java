package jsonAndGSTest;

import java.util.LinkedList;

import jsonAndGS.MyJsonGenerator;
import jsonAndGS.MyJsonNode;
import jsonAndGS.MyJsonTransition;
import jsonAndGS.MyListNodes;
import jsonAndGS.MyListTransitions;
import junit.framework.TestCase;

public class MyJsonGeneratorTest extends TestCase {

public void testGenerateJson() {

		MyJsonGenerator generator = new MyJsonGenerator();

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
		MyJsonNode n4 = new MyJsonNode("node 4", new LinkedList<String>());
		MyJsonNode n5 = new MyJsonNode("node 5", new LinkedList<String>());
		MyJsonNode n6 = new MyJsonNode("node 6", new LinkedList<String>());
		MyJsonNode n7 = new MyJsonNode("node 7", new LinkedList<String>());
		MyJsonNode n8 = new MyJsonNode("node 8", new LinkedList<String>());
		MyJsonNode n9 = new MyJsonNode("node 9", new LinkedList<String>());
		mLN.addNode(n1, n2, n3, n4, n5, n6, n7, n8, n9);

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
		MyJsonTransition t4 = new MyJsonTransition("transition 4", "node 1",
				"node 4", new LinkedList<String>());
		MyJsonTransition t5 = new MyJsonTransition("transition 5", "node 4",
				"node 7", new LinkedList<String>());
		MyJsonTransition t6 = new MyJsonTransition("transition 6", "node 6",
				"node 5", new LinkedList<String>());
		MyJsonTransition t7 = new MyJsonTransition("transition 7", "node 5",
				"node 7", new LinkedList<String>());
		MyJsonTransition t8 = new MyJsonTransition("transition 8", "node 6",
				"node 8", new LinkedList<String>());
		MyJsonTransition t9 = new MyJsonTransition("transition 9", "node 8",
				"node 9", new LinkedList<String>());
		MyJsonTransition t10 = new MyJsonTransition("transition 10", "node 9",
				"node 3", new LinkedList<String>());
		mLT.addTransition(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);

		assertEquals(generator.generateJson(mLN, mLT).exists(), true);
	}

}
