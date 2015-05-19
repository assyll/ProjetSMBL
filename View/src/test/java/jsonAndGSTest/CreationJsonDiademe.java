package jsonAndGSTest;

import java.util.LinkedList;

import jsonAndGS.MyJsonGenerator;
import jsonAndGS.MyJsonNode;
import jsonAndGS.MyJsonEdge;
import jsonAndGS.MyListNodes;
import jsonAndGS.MyListEdges;

public class CreationJsonDiademe {

	public static void main(String[] args) {

		MyJsonGenerator generator = new MyJsonGenerator();

		MyListNodes mLN = new MyListNodes();
		MyListEdges mLE = new MyListEdges();

		MyJsonNode n1 = new MyJsonNode("node 1", true, false,
				new LinkedList<String>());
		n1.addAttribut("att 1");
		n1.addAttribut("att 2");
		MyJsonNode n2 = new MyJsonNode("node 2", true, true,
				new LinkedList<String>());
		n2.addAttribut("att 1");
		n2.addAttribut("att 2");
		MyJsonNode n3 = new MyJsonNode("node 3", false, false,
				new LinkedList<String>());
		n3.addAttribut("att 1");
		n3.addAttribut("att 2");
		MyJsonNode n4 = new MyJsonNode("node 4", false, true,
				new LinkedList<String>());

		mLN.addNode(n1, n2, n3, n4);

		MyJsonEdge e1 = new MyJsonEdge("edge 1", "node 1", "node 2",
				"action !", new LinkedList<String>());
		e1.get_attributs().add("att 1");
		e1.get_attributs().add("att 2");
		MyJsonEdge e2 = new MyJsonEdge("edge 2", "node 2", "node 4",
				"action !", new LinkedList<String>());
		e2.get_attributs().add("att 1");
		e2.get_attributs().add("att 2");
		MyJsonEdge e3 = new MyJsonEdge("edge 3", "node 1", "node 3",
				"action !", new LinkedList<String>());
		e3.get_attributs().add("att 1");
		e3.get_attributs().add("att 2");
		MyJsonEdge e4 = new MyJsonEdge("edge 4", "node 3", "node 4",
				"action !", new LinkedList<String>());

		mLE.addEdges(e1, e2, e3, e4);

		generator.generateJson(mLN, mLE).getAbsolutePath();
	}

}
