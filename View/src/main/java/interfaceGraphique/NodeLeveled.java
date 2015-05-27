package interfaceGraphique;

import org.graphstream.graph.Node;

public class NodeLeveled {
	private Node _node;
	private int _level;
	
	public NodeLeveled(Node node, int level){
		_node = node;
		_level = level;
	}

	public Node get_node() {
		return _node;
	}

	public int get_level() {
		return _level;
	}
	
	public String toString(){
		return _node.toString() + " level " + _level;
	}
}
