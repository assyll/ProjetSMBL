package jsonAndGSTest;

import java.util.LinkedList;

import jsonAndGS.MyJsonEdge;
import jsonAndGS.MyJsonGenerator;
import jsonAndGS.MyJsonNode;
import jsonAndGS.MyListEdges;
import jsonAndGS.MyListNodes;

public class CreationJsonComplexe {

	public static void main(String[] args) {

		MyJsonGenerator generator = new MyJsonGenerator();

		MyListNodes mLN = new MyListNodes();
		MyListEdges mLE = new MyListEdges();

		MyJsonNode n1 = new MyJsonNode("node 1", true, false,
				new LinkedList<String>());
		n1.get_attributs().add("att 1");
		n1.get_attributs().add("att 2");
		MyJsonNode n2 = new MyJsonNode("node 2", false, false,
				new LinkedList<String>());
		n2.get_attributs().add("att 1");
		n2.get_attributs().add("att 2");
		MyJsonNode n3 = new MyJsonNode("node 3", false, false,
				new LinkedList<String>());
		n3.get_attributs().add("att 1");
		n3.get_attributs().add("att 2");
		MyJsonNode n4 = new MyJsonNode("node 4", false, true,
				new LinkedList<String>());
		MyJsonNode n5 = new MyJsonNode("node 5", false, false,
				new LinkedList<String>());
		MyJsonNode n6 = new MyJsonNode("node 6", false, false,
				new LinkedList<String>());
		MyJsonNode n7 = new MyJsonNode("node 7", false, false,
				new LinkedList<String>());
		MyJsonNode n8 = new MyJsonNode("node 8", false, false,
				new LinkedList<String>());
		MyJsonNode n9 = new MyJsonNode("node 9", false, false,
				new LinkedList<String>());
		MyJsonNode n10 = new MyJsonNode("node 10", false, false,
				new LinkedList<String>());
		mLN.addNode(n1, n2, n3, n4, n5, n6, n7, n8, n9, n10);

		MyJsonEdge e1 = new MyJsonEdge("edge 1", "node 1", "node 2",
				"action !", new LinkedList<String>());
		e1.get_attributs().add("att 1");
		e1.get_attributs().add("att 2");
		MyJsonEdge e2 = new MyJsonEdge("edge 2", "node 2", "node 3",
				"action !", new LinkedList<String>());
		e2.get_attributs().add("att 1");
		e2.get_attributs().add("att 2");
		MyJsonEdge e3 = new MyJsonEdge("edge 3", "node 1", "node 3",
				"action !", new LinkedList<String>());
		e3.get_attributs().add("att 1");
		e3.get_attributs().add("att 2");
		MyJsonEdge e4 = new MyJsonEdge("edge 4", "node 1", "node 4",
				"action !", new LinkedList<String>());
		MyJsonEdge e5 = new MyJsonEdge("edge 5", "node 4", "node 7",
				"action !", new LinkedList<String>());
		MyJsonEdge e6 = new MyJsonEdge("edge 6", "node 6", "node 5",
				"action !", new LinkedList<String>());
		MyJsonEdge e7 = new MyJsonEdge("edge 7", "node 5", "node 7",
				"action !", new LinkedList<String>());
		MyJsonEdge e8 = new MyJsonEdge("edge 8", "node 6", "node 8",
				"action !", new LinkedList<String>());
		MyJsonEdge e9 = new MyJsonEdge("edge 9", "node 8", "node 9",
				"action !", new LinkedList<String>());
		MyJsonEdge e10 = new MyJsonEdge("edge 10", "node 9", "node 3",
				"action !", new LinkedList<String>());
		MyJsonEdge e11 = new MyJsonEdge("edge 11", "node 1", "node 10",
				"action !", new LinkedList<String>());
		MyJsonEdge e12 = new MyJsonEdge("edge 12", "node 4", "node 10",
				"action !", new LinkedList<String>());
		mLE.addEdges(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12);

		generator.generateJson(mLN, mLE);
	}
}
