package jsonAndGS;

import java.util.List;

public class MyJsonEdge {
	private String _label;
	private String _nodeB;
	private String _nodeE;
	private String _action;
	private List<String> _attributs;

	public MyJsonEdge(String label, String nodeB, String nodeE, String action,
			List<String> attributs) {
		super();
		_label = label;
		_nodeB = nodeB;
		_nodeE = nodeE;
		_action = action;
		_attributs = attributs;
	}

	public String get_label() {
		return _label;
	}

	public String get_nodeB() {
		return _nodeB;
	}

	public String get_nodeE() {
		return _nodeE;
	}

	public String get_action() {
		return _action;
	}

	public List<String> get_attributs() {
		return _attributs;
	}

}
