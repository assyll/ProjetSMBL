package jsonToGS;

import java.util.List;

public class MyJsonTransition {
	private String _label;
	private String _noeudD;
	private String _noeudA;
	private List<String> _attributs;

	public MyJsonTransition(String label, String noeudD, String noeudA,
			List<String> attributs) {
		super();
		_label = label;
		_noeudD = noeudD;
		_noeudA = noeudA;
		_attributs = attributs;
	}

	public String get_label() {
		return _label;
	}

	public String get_noeudD() {
		return _noeudD;
	}

	public String get_noeudA() {
		return _noeudA;
	}

	public List<String> get_attributs() {
		return _attributs;
	}

}
