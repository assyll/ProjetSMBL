package jsonAndGS;

import java.util.List;

/**
 * <b>MyJsonEdge est la classe repr�sentant une edge</b>
 * <p>
 * Une edge est caract�ris�e par les informations suivantes :
 * </p>
 * <ul>
 * <li>Un label unique</li>
 * <li>Une node de d�part</li>
 * <li>Une node d'arriv�e</li>
 * <li>Une action qui permettra d'identifier la edge dans le graphe</li>
 * </ul>
 * <p>
 * De plus une edge poss�de une liste d'attributs il sera possible d'ajouter des
 * attributs � cette liste
 * </p>
 * 
 * @author hugo
 * @version 1.0
 */
public class MyJsonEdge {

	/**
	 * Le label de la node
	 * 
	 * @see MyJsonEdge#MyJsonEdge(String, String, String, String, List)
	 * @see MyJsonEdge#get_label()
	 */
	private String _label;

	/**
	 * Le nom de la node de d�part
	 * 
	 * @see MyJsonEdge#MyJsonEdge(String, String, String, String, List)
	 * @see MyJsonEdge#get_nodeB()
	 */
	private String _nodeB;

	/**
	 * Le nom de la node d'arriv�e
	 * 
	 * @see MyJsonEdge#MyJsonEdge(String, String, String, String, List)
	 * @see MyJsonEdge#get_nodeE()
	 */
	private String _nodeE;

	/**
	 * Le nom de l'action repr�sent�e par la edge
	 * 
	 * @see MyJsonEdge#MyJsonEdge(String, String, String, String, List)
	 * @see MyJsonEdge#get_action()
	 */
	private String _action;

	/**
	 * La liste contenant les attributs de la edge
	 * <p>
	 * Il est possible d'ajouter des attributs � cette liste
	 * </p>
	 * 
	 * @see MyJsonEdge#MyJsonEdge(String, String, String, String, List)
	 * @see MyJsonEdge#get_attributs()
	 */
	private List<String> _attributs;

	/**
	 * Constructeur de MyJsonEdge
	 * 
	 * @param label
	 *            Le label de la edge
	 * @param nodeB
	 *            La node d'o� part la edge
	 * @param nodeE
	 *            La node o� arrive la edge
	 * @param action
	 *            L'action repr�sent� par la edge
	 * @param attributs
	 *            La liste des attributs de la edge
	 */
	public MyJsonEdge(String label, String nodeB, String nodeE, String action,
			List<String> attributs) {
		super();
		_label = label;
		_nodeB = nodeB;
		_nodeE = nodeE;
		_action = action;
		_attributs = attributs;
	}

	/**
	 * Retourne le label de la edge
	 * 
	 * @return Le label de la edge
	 * 
	 * @since 1.0
	 */
	public String get_label() {
		return _label;
	}

	/**
	 * Retourne le nom de la node d'o� part la edge
	 * 
	 * @return Le nom de la node d'o� part la edge
	 * 
	 * @since 1.0
	 */
	public String get_nodeB() {
		return _nodeB;
	}

	/**
	 * Retourne le nom de la node o� arrive la edge
	 * 
	 * @return Le nom de la node o� arrive la edge
	 * 
	 * @since 1.0
	 */
	public String get_nodeE() {
		return _nodeE;
	}

	/**
	 * Retourne l'action repr�sent�e par la edge
	 * 
	 * @return L'action repr�sent�e par la edge
	 * 
	 * @since 1.0
	 */
	public String get_action() {
		return _action;
	}

	/**
	 * Ajoute un attribut � la liste des attributs
	 * 
	 * @param attribut
	 *            Le nouvel attribut de la edge
	 * 
	 * @see MyJsonEdge#_attributs
	 * 
	 * @since 1.0
	 */
	public void addAttribut(String attribut) {
		_attributs.add(attribut);
	}

	/**
	 * Retourne la liste des attributs de la edge
	 * 
	 * @return La liste des attributs de la edge
	 * 
	 * @since 1.0
	 */
	public List<String> get_attributs() {
		return _attributs;
	}

}
