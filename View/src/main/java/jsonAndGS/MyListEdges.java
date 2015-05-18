package jsonAndGS;

import java.util.LinkedList;
import java.util.List;

public class MyListEdges {
	private List<MyJsonEdge> _listEdges;

	public MyListEdges() {
		_listEdges = new LinkedList<MyJsonEdge>();
	}

	public void addEdges(MyJsonEdge... edges) {
		for (MyJsonEdge edge : edges) {
			_listEdges.add(edge);
		}
	}

	public List<MyJsonEdge> get_listEdges() {
		return _listEdges;
	}

}
