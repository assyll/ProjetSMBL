package jsonAndGSTest;

import java.util.LinkedList;

import org.graphstream.graph.Graph;

import jsonAndGS.JsonToGS;
import jsonAndGS.MyJsonGenerator;
import jsonAndGS.MyJsonNode;
import jsonAndGS.MyJsonTransition;
import jsonAndGS.MyListNodes;
import jsonAndGS.MyListTransitions;
import junit.framework.TestCase;

public class JsonToGSTest extends TestCase {

	public void testJsonToGS(){
		
		String filePath;
		
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

		mLN.addNode(n1, n2, n3, n4);

		MyJsonTransition t1 = new MyJsonTransition("transition 1", "node 1",
				"node 2", new LinkedList<String>());
		t1.get_attributs().add("att 1");
		t1.get_attributs().add("att 2");
		MyJsonTransition t2 = new MyJsonTransition("transition 2", "node 2",
				"node 4", new LinkedList<String>());
		t2.get_attributs().add("att 1");
		t2.get_attributs().add("att 2");
		MyJsonTransition t3 = new MyJsonTransition("transition 3", "node 1",
				"node 3", new LinkedList<String>());
		t3.get_attributs().add("att 1");
		t3.get_attributs().add("att 2");
		MyJsonTransition t4 = new MyJsonTransition("transition 4", "node 3",
				"node 4", new LinkedList<String>());
		
		mLT.addTransition(t1, t2, t3, t4);

		filePath = generator.generateJson(mLN, mLT).getAbsolutePath();

		Graph graph = JsonToGS.generateGraph(filePath);
		graph.display();
		
		while(true){}
	}
	
}
