package jsonAndGS;

import java.util.List;

public class MyJsonNode {
	private String _name;
	private boolean _source, _final;
	private List<String> _attributs;

	public MyJsonNode(String name, boolean source, boolean fin, List<String> attributs) {
		_name = name;
		_source = source;
		_final = fin;
		_attributs = attributs;
	}

	public String get_name() {
		return _name;
	}

	public boolean is_source() {
		return _source;
	}

	public boolean is_final() {
		return _final;
	}

	public List<String> get_attributs() {
		return _attributs;
	}

}
