package jSonAndGS;

import java.util.List;

public class MyJsonNode {
	private String _name;
	private List<String> _attributs;

	public MyJsonNode(String name, List<String> attributs) {
		_name = name;
		_attributs = attributs;
	}

	public String get_name() {
		return _name;
	}

	public List<String> get_attributs() {
		return _attributs;
	}

}
