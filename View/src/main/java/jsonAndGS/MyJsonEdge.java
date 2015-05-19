package jsonAndGS;

import java.util.List;

/**
 * <b>MyJsonEdge est la classe représentant une edge</b>
 * <p>
 * Une edge est caractérisée par les informations suivantes :
 * <ul>
 * <li>Un label unique</li>
 * <li>Une node de départ</li>
 * <li>Une node d'arrivée</li>
 * <li>Une action qui permettra d'identifier la edge dans le graphe</li>
 * </ul>
 * </p>
 * <p>
 * De plus une edge a une liste d'attributs il sera possible d'ajouter des
 * attributs à cette liste
 * </p>
 * 
 * @author hugo
 * @version 1.0
 */
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
