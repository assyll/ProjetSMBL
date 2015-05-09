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

		assertEquals(generator.generateJson(mLN, mLT).exists(), true);
	}

}
