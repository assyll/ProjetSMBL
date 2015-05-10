package jsonAndGS;

import java.util.List;

public class MyJsonEdge {
	private String _label;
	private String _nodeB;
	private String _nodeE;
	private List<String> _attributs;

	public MyJsonEdge(String label, String nodeB, String nodeE,
			List<String> attributs) {
		super();
		_label = label;
		_nodeB = nodeB;
		_nodeE = nodeE;
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

	public List<String> get_attributs() {
		return _attributs;
	}

}
