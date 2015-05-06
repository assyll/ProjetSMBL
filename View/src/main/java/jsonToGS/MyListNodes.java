package jsonToGS;

import java.util.LinkedList;
import java.util.List;

public class MyListNodes {
	private List<MyJsonNode> _listNodes;

	public MyListNodes() {
		_listNodes = new LinkedList<MyJsonNode>();
	}

	public void addNode(MyJsonNode... nodes) {
		for(MyJsonNode node : nodes){
			_listNodes.add(node);
		}
	}

	public List<MyJsonNode> get_listNodes() {
		return _listNodes;
	}

}
